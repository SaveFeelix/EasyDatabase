package de.savefeelix.database.abstracts;

import de.savefeelix.database.enums.DataBaseType;
import org.jetbrains.annotations.NotNull;

public abstract class Information {
    /**
     * Set the Value to enable/disable the Database
     *
     * @return if the database is enabled
     */
    @NotNull
    public abstract Boolean isEnabled();

    /**
     * Set the Value to set the Hostadress
     *
     * @return the Hostname (IP-Adress or Domain)
     */
    @NotNull
    public abstract String getHost();

    /**
     * Set the Value to set the Port
     *
     * @return the Port
     */
    @NotNull
    public abstract Integer getPort();

    /**
     * <pre>
     * Set the Value to specify the Database
     *
     * IMPORTANT!!!!!!
     * If you're not using the database
     * there will throw an {@link java.sql.SQLException}
     * IMPORTANT!!!!!!
     * </pre>
     *
     * @return the Name of the Database
     */
    @NotNull
    public abstract String getDatabase();

    /**
     * Set the Value to specify the User
     *
     * @return the Username
     */
    @NotNull
    public abstract String getUser();

    /**
     * Set the Value to specify the Password
     *
     * @return the Password
     */
    @NotNull
    public abstract String getPassword();

    /**
     * Set the Connection URL for the Connection
     *
     * @param databaseType Type of the Database to specify the ({@link DataBaseType})
     * @return the Connection URL from the specific Database
     */
    public String getConnectionURL(@NotNull DataBaseType databaseType) {
        switch (databaseType) {
            case MySQL:
                return "jdbc:mysql://" + this.getHost() + ":" + +this.getPort() + "/" + this.getDatabase();
            case MariaDB:
                return "jdbc:mariadb://" + this.getHost() + ":" + +this.getPort() + "/" + this.getDatabase();
        }
        return null;
    }
}