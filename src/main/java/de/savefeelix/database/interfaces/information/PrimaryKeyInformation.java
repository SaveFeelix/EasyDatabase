package de.savefeelix.database.interfaces.information;

import de.savefeelix.database.enums.ReferenceTypes;
import org.jetbrains.annotations.NotNull;

public interface PrimaryKeyInformation {
    /**
     * Set the Value to specify the Table Name
     *
     * @return the Table name
     */
    @NotNull String getTableName();

    /**
     * Set the Value to specify the Column Name
     *
     * @return the Column Name
     */
    @NotNull String getColumnName();

    /**
     * Set the Value to specify the Reference of "on update"
     *
     * @return the {@link ReferenceTypes}
     */
    @NotNull ReferenceTypes onUpdate();

    /**
     * Set the Value to specify the Reference of "on delete"
     *
     * @return the {@link ReferenceTypes}
     */
    @NotNull ReferenceTypes onDelete();
}