package ru.nsu.korolev.stackcalculator;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.korolev.stackcalculator.commands.Command;
import ru.nsu.korolev.stackcalculator.commands.CommandFactory;
import ru.nsu.korolev.stackcalculator.commands.CommandParser;

import java.nio.file.Path;

public class StackCalculator {
    private static final Logger logger = LogManager.getLogger(StackCalculator.class);
    private final CalculatorContext context;
    private final CommandLine instructionFile;
    Option CLIOption;

    public StackCalculator(String[] args) {
        context = new CalculatorContext();

        Options cliOptions = new Options();
        cliOptions.addOption("i", "input", true, "commands input file path");
        CommandLineParser parser = new DefaultParser();
        try {
            instructionFile = parser.parse(cliOptions, args);
        } catch (ParseException e) {
            throw new RuntimeException("Apache CLI parser failed.",e);
        }
    }

    public void run() {
        CommandParser commandParser;

        if(instructionFile.hasOption(CLIOption)){
            Path commandFilePath = Path.of(instructionFile.getOptionValue(CLIOption));
            commandParser = new CommandParser(commandFilePath);
            logger.debug("Created parser with {}", commandFilePath);
        }
        else {
            commandParser = new CommandParser();
            logger.debug("Created parser from stdin");
        }

        CommandFactory factory = new CommandFactory();

        Optional<CommandParser.Data> commandData;
        while ((commandData = commandParser.parseNext()).isPresent()){
            try {
                Command command = factory.create(commandData.get());
                command.execute(context);
            }
            catch (CommandExecuteException | WrongArgumentsException | UnknownCommandException e){
                logger.error(e.getMessage());
            }
        }
        commandParser.close();
    }
}