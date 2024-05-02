package ru.nsu.korolev.stackcalculator;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.korolev.stackcalculator.commands.Command;
import ru.nsu.korolev.stackcalculator.commands.CommandFactory;
import ru.nsu.korolev.stackcalculator.commands.CommandParser;
import ru.nsu.korolev.stackcalculator.commands.exceptions.CommandExecuteException;
import ru.nsu.korolev.stackcalculator.commands.exceptions.UnknownCommandException;
import ru.nsu.korolev.stackcalculator.commands.exceptions.WrongArgsException;

import java.nio.file.Path;
import java.util.Optional;

public class StackCalculator {
    private static final Logger logger = LogManager.getLogger(StackCalculator.class);
    private final CommandLine instructionFile;
    public final CalculatorContext context;
    Option CLIOption;

    public StackCalculator(String[] args) {
        context = new CalculatorContext();

        Options cliOptions = new Options();
        CLIOption = Option.builder().option("i").longOpt("input").hasArg().desc("commands input file path").build();
        cliOptions.addOption(CLIOption);
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
            catch (WrongArgsException | UnknownCommandException e){
                logger.warn(e.getMessage());
            }
            catch (CommandExecuteException e){
                logger.error(e.getMessage());
            }
        }
        commandParser.close();
    }
}
