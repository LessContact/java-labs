package ru.nsu.korolev.stackcalculator.commands.implementation;

import org.apache.logging.log4j.LogManager;
import ru.nsu.korolev.stackcalculator.CalculatorContext;
import ru.nsu.korolev.stackcalculator.commands.Command;
import ru.nsu.korolev.stackcalculator.commands.CommandParser;
import ru.nsu.korolev.stackcalculator.commands.exceptions.CommandExecuteException;

public class pop extends Command {
    public pop(CommandParser.Data commandData) {
        super(commandData.CommandText(), commandData.line());
    }
    public pop(){
        super();
    }

    @Override
    public void execute(CalculatorContext context) throws CommandExecuteException {
        if (context.isStackEmpty()) {
            throw new CommandExecuteException(getCommandText(), getLineNumber(), "stack is empty");
        }

        double val = context.popValue();
        LogManager.getLogger(pop.class).debug("Popped value {} from stack", val);
    }
}
