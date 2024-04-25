package ru.nsu.korolev.stackcalculator.commands.implementation;

import org.apache.logging.log4j.LogManager;
import ru.nsu.korolev.stackcalculator.CalculatorContext;
import ru.nsu.korolev.stackcalculator.commands.Command;
import ru.nsu.korolev.stackcalculator.commands.CommandParser;
import ru.nsu.korolev.stackcalculator.commands.exceptions.CommandExecuteException;
import ru.nsu.korolev.stackcalculator.commands.exceptions.UnknownCommandException;
import ru.nsu.korolev.stackcalculator.commands.exceptions.WrongArgsException;

public class push extends Command {
    private double value;
    private final String varName;

    public push(CommandParser.Data commandData) {
        super(commandData.CommandText(), commandData.line());

        if (commandData.args().length < 1) {
            throw new WrongArgsException(getCommandText(), getLineNumber(), 1, commandData.args().length);
        }

        if (commandData.args()[0].chars().allMatch(Character::isLetter)) {
            varName = commandData.args()[0];
        }
        else {
            varName = null;
            try {
                value = Double.parseDouble(commandData.args()[0]);
            } catch (NumberFormatException e) {
                throw new WrongArgsException(getCommandText(), getLineNumber(), "failed to parse floating point value");
            }
        }
    }
    @Override
    public void execute(CalculatorContext context) throws CommandExecuteException {
        if (varName != null) {
            try {
                value = context.getParameter(varName);
            } catch (UnknownCommandException e) {
                throw new CommandExecuteException(getCommandText(), getLineNumber(), "Given variable " + varName + " is undefined");
            }
        }

        context.pushValue(value);
        LogManager.getLogger(push.class).debug("Pushed value {} on the stack", value);
    }
}
