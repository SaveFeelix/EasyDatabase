package de.savefeelix.database.enums;

public enum DataBaseType {
    MySQL("MySQL"),
    MariaDB("MariaDB"),
    ;

    private final String name;

    DataBaseType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}