package de.savefeelix.database.interfaces;

import de.savefeelix.database.abstracts.Information;
import de.savefeelix.database.enums.DataBaseValueType;
import de.savefeelix.database.interfaces.information.PrimaryKeyInformation;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

public interface DataBase {

    /**
     * Try to open the Connection to DefaultDatabase
     *
     * @param information Information about the Connection
     *                    This include:
     *                    - isEnabled - Show you if the database is Enabled
     *                    - Host - IP-Address/HostName of the DefaultDatabase-Server
     *                    - Port - Port of the DefaultDatabase-Instance
     *                    - DefaultDatabase - Name of the DefaultDatabase
     *                    - User - Name of the User
     *                    - Password - Password of the User
     * @return true (if they connect successfully), false (is they failed to connect)
     */
    boolean openConnection(@NotNull Information information);

    /**
     * Close the Connection
     *
     * @param errorMessages Other Error Messages
     *                      If errorMessages is null the normal Error Messages will be printed.
     */
    void closeConnection(String... errorMessages);

    /**
     * Method to create a Table in the DefaultDatabase
     *
     * @param tableName Table Name
     */
    void createTable(@NotNull String tableName);

    /**
     * Add a Column
     *
     * @param tableName  Name of the Table
     * @param columnName Name of the Column
     * @param type       Type of the Column (Use the Enum DataBaseValueType)
     * @param isNotNull  Set the Column Not Null
     */
    void addColumn(@NotNull String tableName, @NotNull String columnName, @NotNull DataBaseValueType type, @NotNull Boolean isNotNull);

    /**
     * Add a Column as Primarykey
     *
     * @param tableName  Name of the Table
     * @param columnName Name of the Column
     * @param type       Type of the Column
     * @param isNotNull  Set the Column Not Null
     */
    void addPrimaryColumn(@NotNull String tableName, @NotNull String columnName, @NotNull DataBaseValueType type, @NotNull Boolean isNotNull);

    /**
     * Add a Column as ForeignKey
     *
     * @param tableName             Name of the Table
     * @param columnName            Name of the Column
     * @param type                  Type of the Column
     * @param isNotNull             Set the Column Not Null
     * @param primaryKeyInformation Information to the Primary Key
     */
    void addForeignColumn(@NotNull String tableName, @NotNull String columnName, @NotNull DataBaseValueType type, @NotNull Boolean isNotNull, @NotNull PrimaryKeyInformation primaryKeyInformation);

    /**
     * Getter of the Connection
     *
     * @return the Connection
     */
    Connection getConnection();
}