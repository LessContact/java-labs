package ru.nsu.korolev.stackcalculator.commands.implementation;

import org.apache.logging.log4j.LogManager;
import ru.nsu.korolev.stackcalculator.CalculatorContext;
import ru.nsu.korolev.stackcalculator.commands.Command;
import ru.nsu.korolev.stackcalculator.commands.CommandParser;
import ru.nsu.korolev.stackcalculator.commands.exceptions.CommandExecuteException;

public class print extends Command {
    public print(CommandParser.Data commandData) {
        super(commandData.CommandText(), commandData.line());
    }

    @Override
    public void execute(CalculatorContext context) throws CommandExecuteException {
        if (context.isStackEmpty()) {
            context.getOutputStream().println("Stack is empty, nothing to print");
            LogManager.getLogger(print.class).debug("Stack is empty, nothing to print");
        }
        else {
            double value = context.peekValue();
            context.getOutputStream().printf("Top of the stack: %f%n", value);
            LogManager.getLogger(print.class).debug("Printed top of the stack: {}", value);
        }
    }
}
