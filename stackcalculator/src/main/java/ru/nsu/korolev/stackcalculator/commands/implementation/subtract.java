package ru.nsu.korolev.stackcalculator.commands.implementation;

import org.apache.logging.log4j.LogManager;
import ru.nsu.korolev.stackcalculator.CalculatorContext;
import ru.nsu.korolev.stackcalculator.commands.Command;
import ru.nsu.korolev.stackcalculator.commands.CommandParser;
import ru.nsu.korolev.stackcalculator.commands.exceptions.CommandExecuteException;
import ru.nsu.korolev.stackcalculator.commands.exceptions.WrongArgsException;

public class subtract extends Command {
    public subtract(CommandParser.Data commandData) {
        super(commandData.CommandText(), commandData.line());
    }
    public subtract(){
        super();
    }

    @Override
    public void execute(CalculatorContext context) throws CommandExecuteException {
        if (context.stackSize() < 2) {
            throw new WrongArgsException(getCommandText(), getLineNumber(), 2, context.stackSize());
        }

        double a = context.popValue();
        double b = context.popValue();
        double result = a - b;
        context.pushValue(result);
        LogManager.getLogger(subtract.class).debug("Executed subtract command: {} - {} = {}", a, b, result);
    }
}
