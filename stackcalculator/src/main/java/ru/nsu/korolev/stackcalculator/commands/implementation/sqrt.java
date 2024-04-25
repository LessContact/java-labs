package ru.nsu.korolev.stackcalculator.commands.implementation;

import org.apache.logging.log4j.LogManager;
import ru.nsu.korolev.stackcalculator.CalculatorContext;
import ru.nsu.korolev.stackcalculator.commands.Command;
import ru.nsu.korolev.stackcalculator.commands.CommandParser;
import ru.nsu.korolev.stackcalculator.commands.exceptions.CommandExecuteException;
import ru.nsu.korolev.stackcalculator.commands.exceptions.WrongArgsException;

public class sqrt extends Command {
    public sqrt(CommandParser.Data commandData) {
        super(commandData.CommandText(), commandData.line());
    }

    @Override
    public void execute(CalculatorContext context) throws CommandExecuteException {
        if (context.stackSize() < 1) {
            throw new WrongArgsException(getCommandText(), getLineNumber(), 1, context.stackSize());
        }

        double a = context.popValue();
        if (a < 0) {
            throw new CommandExecuteException(getCommandText(), getLineNumber(), "taking square root of negative value is impossible (thats beyond my scope, man)");
        }

        double result = Math.sqrt(a);
        context.pushValue(result);
        LogManager.getLogger(sqrt.class).debug("Executed sqrt command: sqrt({})= {}", a, result);
    }
}
