package ru.nsu.korolev.stackcalculator.commands.exceptions;

public class WrongArgsException extends RuntimeException {
    public WrongArgsException(String message) {
        super(message);
    }
    public WrongArgsException(String command, int line, int expected, int received) {
        super("Wrong arguments in " + command + " at line " + line + ", expected " + expected + " but got " + received);
    }
    public WrongArgsException(String command, int line, String message) {
        super(command + ":" + " at line " + line + ", -- " + message);
    }
}
