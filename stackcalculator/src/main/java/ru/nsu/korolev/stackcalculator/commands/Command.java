package ru.nsu.korolev.stackcalculator.commands;

import ru.nsu.korolev.stackcalculator.CalculatorContext;

public abstract class Command {
    private final String CommandText;
    private final int lineNumber;

    public Command(String CommandText, int lineNumber) {
        this.CommandText = CommandText;
        this.lineNumber = lineNumber;
    }
    public Command(){
        this.CommandText = "";
        this.lineNumber = -1;
    }

    protected String getCommandText() {
        return CommandText;
    }

    protected int getLineNumber() {
        return lineNumber;
    }

    public abstract void execute(CalculatorContext context) throws RuntimeException;
}
