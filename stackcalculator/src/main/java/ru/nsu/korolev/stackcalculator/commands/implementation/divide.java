package ru.nsu.korolev.stackcalculator.commands.implementation;

import org.apache.logging.log4j.LogManager;
import ru.nsu.korolev.stackcalculator.CalculatorContext;
import ru.nsu.korolev.stackcalculator.commands.Command;
import ru.nsu.korolev.stackcalculator.commands.CommandParser;
import ru.nsu.korolev.stackcalculator.commands.exceptions.CommandExecuteException;
import ru.nsu.korolev.stackcalculator.commands.exceptions.WrongArgsException;

public class divide extends Command {
    public divide(CommandParser.Data commandData) {
        super(commandData.CommandText(), commandData.line());
    }
    public divide(){
        super();
    }

    @Override
    public void execute(CalculatorContext context) throws CommandExecuteException {
        if (context.stackSize() < 2) {
            throw new WrongArgsException(getCommandText(), getLineNumber(), 2, context.stackSize());
        }

        double a = context.popValue();
        double b = context.popValue();

        if (b == 0) {
            context.pushValue(b);
            context.pushValue(a);
            throw new CommandExecuteException(getCommandText(), getLineNumber(), "division by zero is not allowed");
        }

        double result = a / b;
        context.pushValue(result);
        LogManager.getLogger(divide.class).debug("Executed divide command: {} / {} = {}", a, b, result);
    }
}
