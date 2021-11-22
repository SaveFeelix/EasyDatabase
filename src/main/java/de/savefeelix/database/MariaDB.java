package de.savefeelix.database;

import de.savefeelix.database.abstracts.Information;
import de.savefeelix.database.enums.ConsoleColor;
import de.savefeelix.database.enums.Data;
import de.savefeelix.database.enums.DataBaseType;
import de.savefeelix.database.enums.DataBaseValueType;
import de.savefeelix.database.interfaces.DataBase;
import de.savefeelix.database.interfaces.information.PrimaryKeyInformation;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public abstract class MariaDB implements DataBase {

    protected Connection connection;

    @Override
    public boolean openConnection(@NotNull Information information) {
        if (information.isEnabled()) {
            try {
                if (connection != null)
                    return false;
                System.out.println((Data.LookingForDriver.getText().replace("%type%", DataBaseType.MariaDB.getName())));
                Class.forName("org.mariadb.jdbc.Driver");
                System.out.println(Data.DriverLoad.getText());

                System.out.println(Data.Connecting.getText());
                connection = DriverManager.getConnection(information.getConnectionURL(DataBaseType.MariaDB), information.getUser(), information.getPassword());
                System.out.println(Data.Connected.getText());
                createDefaultTables();
                createDefaultColumns();
                return true;
            } catch (ClassNotFoundException | SQLException e) {
                if (e instanceof ClassNotFoundException)
                    System.out.println(Data.FailedToLoadDriver.getText());
                else System.out.println(Data.FailedToConnect.getText());
                System.out.println(ConsoleColor.RED.code + " " + e.getMessage());
            }
        } else
            System.out.println(Data.DatabaseNotEnabled.getText());
        return false;
    }

    public abstract void createDefaultTables();

    public abstract void createDefaultColumns();

    @Override
    public void closeConnection(String... errorMessages) {
        try {
            if (connection != null) {
                connection.close();
                System.out.println(Data.ConnectionClosed.getText());
            }
        } catch (SQLException e) {
            if (errorMessages != null) {
                for (String errorMessage : errorMessages)
                    System.out.println(ConsoleColor.RED.code + " " + errorMessage);
            } else
                System.out.println(ConsoleColor.RED.code + " " + e.getMessage());
        }
    }

    @Override
    public void createTable(@NotNull String tableName) {
        this.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "noNameRow BINARY NULL" +
                ")");
    }

    public boolean isPrimaryKey(@NotNull String tableName) {
        try {
            return this.executeQuery("SELECT C.COLUMN_NAME FROM information_schema.TABLE_CONSTRAINTS T JOIN information_schema.KEY_COLUMN_USAGE C ON C.CONSTRAINT_NAME=T.CONSTRAINT_NAME WHERE C.TABLE_NAME='" + tableName + "' and T.CONSTRAINT_TYPE='PRIMARY KEY'").next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isForeignKey(@NotNull String tableName) {
        try {
            return this.executeQuery("SELECT C.COLUMN_NAME FROM information_schema.TABLE_CONSTRAINTS T JOIN information_schema.KEY_COLUMN_USAGE C ON C.CONSTRAINT_NAME=T.CONSTRAINT_NAME WHERE C.TABLE_NAME='" + tableName + "' and T.CONSTRAINT_TYPE='FOREIGN KEY'").next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void addColumn(@NotNull String tableName, @NotNull String columnName, @NotNull DataBaseValueType type, @NotNull Boolean isNotNull) {
        String commandName = "ALTER TABLE " + tableName + " ADD IF NOT EXISTS " + columnName + " " + type.getTypeName();
        commandName += (isNotNull) ? " NOT NULL;" : "NULL;";
        this.executeUpdate(commandName);
        this.executeUpdate("ALTER TABLE " + tableName + " DROP COLUMN IF EXISTS noNameRow;");
    }

    @Override
    public void addPrimaryColumn(@NotNull String tableName, @NotNull String columnName, @NotNull DataBaseValueType type, @NotNull Boolean isNotNull) {
        this.addColumn(tableName, columnName, type, isNotNull);
        if (!isPrimaryKey(tableName)) {
            String commandName = "ALTER TABLE " + tableName + " ADD PRIMARY KEY (" + columnName + ");";
            this.executeUpdate(commandName);
        }
    }

    @Override
    public void addForeignColumn(@NotNull String tableName, @NotNull String columnName, @NotNull DataBaseValueType type, @NotNull Boolean isNotNull, @NotNull PrimaryKeyInformation primaryKeyInformation) {
        this.addColumn(tableName, columnName, type, isNotNull);
        if (!isForeignKey(tableName)) {
            String commandName = "ALTER TABLE " + tableName + " " +
                    "ADD FOREIGN KEY (" + columnName + ") REFERENCES " + primaryKeyInformation.getTableName() + "(" + primaryKeyInformation.getColumnName() + ")" +
                    " ON UPDATE " + primaryKeyInformation.onUpdate() + " ON DELETE " + primaryKeyInformation.onDelete();
            this.executeUpdate(commandName);
        }
    }

    @Override
    public ResultSet executeQuery(String command) {
        try (Statement statement = connection.createStatement()) {
            return statement.executeQuery(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void executeUpdate(String command) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
