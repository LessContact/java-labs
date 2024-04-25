package ru.nsu.korolev.stackcalculator.commands;

import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

public class CommandParser {

    private final BufferedReader reader;
    public record Data(String name, String[] args, String CommandText, int line) {}
    private int currentLineNumber = 0;

    public CommandParser(Path filePath) {
        try {
            File file = new File(String.valueOf(filePath));
            if (!file.exists()) {
                LogManager.getLogger().error("Failed to open file: {}", filePath);
                throw new FileNotFoundException();
            }

            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public CommandParser() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public Optional<Data> parseNext() {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                ++currentLineNumber;
                if (line.startsWith("#") || line.isEmpty()) continue;
                if (line.equalsIgnoreCase("exit")) {
                    return Optional.empty();
                }

                final var parsedLine = line.split(" ");
                final var commandName = parsedLine[0];
                final var args = Arrays.copyOfRange(parsedLine, 1, parsedLine.length);

                return Optional.of(new Data(commandName, args, line, currentLineNumber));
            }
        } catch (IOException e) {
            LogManager.getLogger().error("Failed to read next command! {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close input stream!", e);
        }
    }
}
