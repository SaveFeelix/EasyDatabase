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

public abstract class MySQL implements DataBase {
    private Connection connection;

    @Override
    public boolean openConnection(@NotNull Information information) {
        if (information.isEnabled()) {
            try {
                if (connection != null)
                    return false;
                System.out.println(Data.LookingForDriver.getText().replace("%type%", DataBaseType.MySQL.getName()));
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println(Data.DriverLoad.getText());

                System.out.println(Data.Connecting.getText());
                connection = DriverManager.getConnection(information.getConnectionURL(DataBaseType.MySQL), information.getUser(), information.getPassword());
                System.out.println(Data.Connected.getText());

                System.out.println(Data.CreateTable.getText());
                createDefaultTables();
                System.out.println(Data.CreateColumns.getText());
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
            if (connection != null)
                connection.close();
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
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                    "noNameRow BINARY NULL" +
                    ")");
        } catch (SQLException e) {
            System.out.println(Data.FailedToCreateTable.getText());
        }
    }

    public boolean isPrimaryKey(@NotNull String tableName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT C.COLUMN_NAME FROM information_schema.TABLE_CONSTRAINTS T JOIN information_schema.KEY_COLUMN_USAGE C ON C.CONSTRAINT_NAME=T.CONSTRAINT_NAME WHERE C.TABLE_NAME=? and T.CONSTRAINT_TYPE='PRIMARY KEY'")) {
            preparedStatement.setString(1, tableName);
            preparedStatement.execute();
            ResultSet result = preparedStatement.getResultSet();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isForeignKey(@NotNull String tableName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT C.COLUMN_NAME FROM information_schema.TABLE_CONSTRAINTS T JOIN information_schema.KEY_COLUMN_USAGE C ON C.CONSTRAINT_NAME=T.CONSTRAINT_NAME WHERE C.TABLE_NAME=? and T.CONSTRAINT_TYPE='FOREIGN KEY'")) {
            preparedStatement.setString(1, tableName);
            preparedStatement.execute();
            ResultSet result = preparedStatement.getResultSet();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void addColumn(@NotNull String tableName, @NotNull String columnName, @NotNull DataBaseValueType type, @NotNull Boolean isNotNull) {
        try (Statement statement = connection.createStatement()) {
            String commandName = "ALTER TABLE " + tableName + " ADD IF NOT EXISTS " + columnName + " " + type.getTypeName();
            commandName += (isNotNull) ? " NOT NULL;" : "NULL;";
            statement.executeUpdate(commandName);
            statement.executeUpdate("ALTER TABLE " + tableName + " DROP COLUMN IF EXISTS noNameRow;");
        } catch (SQLException e) {
            System.out.println(Data.FailedToAddColumn.getText());
        }
    }

    @Override
    public void addPrimaryColumn(@NotNull String tableName, @NotNull String columnName, @NotNull DataBaseValueType type, @NotNull Boolean isNotNull) {
        try (Statement statement = connection.createStatement()) {
            this.addColumn(tableName, columnName, type, isNotNull);
            if (!isPrimaryKey(tableName)) {
                String commandName = "ALTER TABLE " + tableName + " ADD PRIMARY KEY (" + columnName + ");";
                statement.executeUpdate(commandName);
            }
        } catch (SQLException e) {
            System.out.println(Data.FailedToAddPrimaryColumn.getText());
        }
    }

    @Override
    public void addForeignColumn(@NotNull String tableName, @NotNull String columnName, @NotNull DataBaseValueType type, @NotNull Boolean isNotNull, @NotNull PrimaryKeyInformation primaryKeyInformation) {
        try (Statement statement = connection.createStatement()) {
            this.addColumn(tableName, columnName, type, isNotNull);
            if (!isForeignKey(tableName)) {
                String commandName = "ALTER TABLE " + tableName + " " +
                        "ADD FOREIGN KEY (" + columnName + ") REFERENCES " + primaryKeyInformation.getTableName() + "(" + primaryKeyInformation.getColumnName() + ")" +
                        " ON UPDATE " + primaryKeyInformation.onUpdate() + " ON DELETE " + primaryKeyInformation.onDelete();
                statement.executeUpdate(commandName);
            }
        } catch (SQLException e) {
            System.out.println(Data.FailedToAddForeignColumn.getText());
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}