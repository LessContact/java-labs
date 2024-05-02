package ru.nsu.korolev.stackcalculator.commands;

import ru.nsu.korolev.stackcalculator.commands.exceptions.CommandExecuteException;
import ru.nsu.korolev.stackcalculator.commands.exceptions.UnknownCommandException;
import ru.nsu.korolev.stackcalculator.commands.exceptions.WrongArgsException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class CommandFactory {
    public static final String commandMap = "/commands.properties";

    private final Properties mappings;
    public CommandFactory() {
        mappings = new Properties();
        try (InputStream stream = CommandFactory.class.getResourceAsStream(commandMap)) {
            mappings.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load command mappings!", e);
        }
    }

    public Command create(CommandParser.Data commandData) throws WrongArgsException {
        String commandClassPath = mappings.getProperty(commandData.name());
        if (commandClassPath == null) {
            throw new UnknownCommandException(commandData.name(), commandData.line());
        }
        try {
            return (Command) Class.forName(commandClassPath).getDeclaredConstructor(commandData.getClass()).newInstance(commandData);
        } catch (InstantiationException | ClassNotFoundException |
                 NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException("Failed to create the command", e);
        }
        catch (InvocationTargetException e) {
            Throwable constructorException = e.getTargetException();
            if (constructorException instanceof WrongArgsException) {
                throw (WrongArgsException) constructorException;
            }
            if (constructorException instanceof CommandExecuteException) {
                throw (CommandExecuteException) constructorException;
            }
            else throw new RuntimeException("Failed to create desired command class!", e);
        }
    }
}