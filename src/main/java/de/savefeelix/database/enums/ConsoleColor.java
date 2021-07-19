package de.savefeelix.database.enums;

import java.util.concurrent.atomic.AtomicReference;

public enum ConsoleColor {

    // Normal
    BLACK((char)27 + "[30m"),
    RED((char)27 + "[31m"),
    GREEN((char)27 + "[32m"),
    YELLOW((char)27 + "[33m"),
    BLUE((char)27 + "[34m"),
    MAGENTA((char)27 + "[35m"),
    CYAN((char)27 + "[37m"),
    WHITE((char)27 + "[37m"),

    // Brighter
    BRIGHT_BLACK((char)27 + "[30;1m"),
    BRIGHT_RED((char)27 + "[31;1m"),
    BRIGHT_GREEN((char)27 + "[32;1m"),
    BRIGHT_YELLOW((char)27 + "[33;1m"),
    BRIGHT_BLUE((char)27 + "[34;1m"),
    BRIGHT_MAGENTA((char)27 + "[35;1m"),
    BRIGHT_CYAN((char)27 + "[36;1m"),
    BRIGHT_WHITE((char)27 + "[37;1m"),

    // Background
    BACKGROUND_BLACK((char)27 + "[40m"),
    BACKGROUND_RED((char)27 + "[41m"),
    BACKGROUND_GREEN((char)27 + "[42m"),
    BACKGROUND_YELLOW((char)27 + "[43m"),
    BACKGROUND_BLUE((char)27 + "[44m"),
    BACKGROUND_MAGENTA((char)27 + "[45m"),
    BACKGROUND_CYAN((char)27 + "[46m"),
    BACKGROUND_WHITE((char)27 + "[47m"),

    // Brighter-Background
    BACKGROUND_BRIGHT_BLACK((char)27 + "[40;1m"),
    BACKGROUND_BRIGHT_RED((char)27 + "[41;1m"),
    BACKGROUND_BRIGHT_GREEN((char)27 + "[42;1m"),
    BACKGROUND_BRIGHT_YELLOW((char)27 + "[43;1m"),
    BACKGROUND_BRIGHT_BLUE((char)27 + "[44;1m"),
    BACKGROUND_BRIGHT_MAGENTA((char)27 + "[45;1m"),
    BACKGROUND_BRIGHT_CYAN((char)27 + "[46;1m"),
    BACKGROUND_BRIGHT_WHITE((char)27 + "[47;1m"),

    // Utils
    BOLD((char)27 + "[1m"),
    UNDERLINED((char)27 + "[4m"),
    INVERTED((char)27 + "[7m"),
    RESET((char)27 + "[0m"),
    ;

    public final String code;

    ConsoleColor(String code) {
        this.code = code;
    }

    public static String removeColorCodes(String message) {
        AtomicReference<String> tmpMessage = new AtomicReference<>(message);
        for (ConsoleColor value : values()) {
            if (tmpMessage.get().contains(value.code))
                tmpMessage.set(message.replaceAll(value.code, ""));
        }
        return tmpMessage.get();
    }
}