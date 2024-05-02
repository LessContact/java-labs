package ru.nsu.korolev.stackcalculator.commands.exceptions;

public class UnknownCommandException extends RuntimeException {
    public UnknownCommandException(String commandName, int line) {
        super("Unknown command: " + commandName + "at line " + line);
    }
//    public UnknownCommandException(String commandName, int line, String message) {
//        super("Unknown command: " + commandName + "at line " + line + "\n" + message);
//    }
    public UnknownCommandException() {
        super("Unknown variable");
    }
}
