package de.savefeelix.database.enums;

public enum Data {

    // Database
    // Reply
    LookingForDriver(ConsoleColor.CYAN.code + "Looking for %type%-Driver..."),
    DriverLoad(ConsoleColor.GREEN.code + "Driver loaded"),
    Connecting(ConsoleColor.CYAN.code + "Connecting..."),
    Connected(ConsoleColor.GREEN.code + "Connected"),

    CreateTable(ConsoleColor.GREEN.code + "Create Tables..."),
    CreateColumns(ConsoleColor.GREEN.code + "Create Columns..."),

    // Error
    FailedToConnect(ConsoleColor.RED.code + "Failed to connect to Database§7!"),
    FailedToLoadDriver(ConsoleColor.RED.code + "Failed to load Driver§7!"),
    DatabaseNotEnabled(ConsoleColor.RED.code + "Please enable the Database§7!"),


    FailedToCreateTable(ConsoleColor.RED.code + "Cannot create Table %name%§7!"),
    FailedToAddColumn(ConsoleColor.RED.code + "Cannot add Column to the Table§7!"),
    FailedToAddPrimaryColumn(ConsoleColor.RED.code + "Cannot add Primary Column to the Table§7!"),
    FailedToAddForeignColumn(ConsoleColor.RED.code + "Cannot add Foreign Column to the Table§7!"),

    // Normal
    ConnectionClosed(ConsoleColor.GREEN.code + "Connection closed"),
    ;

    private final String text;

    Data(String text) {
        this.text = text;
    }

    public String getText() {
        return text + ConsoleColor.RESET.code;
    }
}