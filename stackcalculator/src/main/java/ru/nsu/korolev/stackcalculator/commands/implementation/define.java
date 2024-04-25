package ru.nsu.korolev.stackcalculator.commands.implementation;

import org.apache.logging.log4j.LogManager;
import ru.nsu.korolev.stackcalculator.CalculatorContext;
import ru.nsu.korolev.stackcalculator.commands.Command;
import ru.nsu.korolev.stackcalculator.commands.CommandParser;
import ru.nsu.korolev.stackcalculator.commands.exceptions.CommandExecuteException;
import ru.nsu.korolev.stackcalculator.commands.exceptions.WrongArgsException;

public class define extends Command {
    private final String name;
    private final double value;

    public define(CommandParser.Data commandData) {
        super(commandData.CommandText(), commandData.line());

        if (commandData.args().length < 2) {
            throw new WrongArgsException(getCommandText(), getLineNumber(), 2, commandData.args().length);
        }

        name = commandData.args()[0];
        if (!name.chars().allMatch(Character::isLetter)) {
            throw new WrongArgsException(getCommandText(), getLineNumber(), "variable name must contain only letter symbols");
        }

        try {
            value = Double.parseDouble(commandData.args()[1]);
        } catch (NumberFormatException e) {
            throw new CommandExecuteException(getCommandText(), getLineNumber(), "failed to parse floating point value");
        }
    }

    @Override
    public void execute(CalculatorContext context) throws CommandExecuteException {
        Double old = context.addParameter(name, value);
        LogManager.getLogger(define.class).debug("Defined parameter {} with value {}", name, value);
    }
}
