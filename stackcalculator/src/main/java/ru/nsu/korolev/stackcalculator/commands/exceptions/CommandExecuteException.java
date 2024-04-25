package ru.nsu.korolev.stackcalculator.commands.exceptions;

public class CommandExecuteException extends RuntimeException {
    public CommandExecuteException(String CommandText, int line, String message) {
        super("Failed to execute command " + CommandText + " at line " + line + ": " + message);
    }
}
