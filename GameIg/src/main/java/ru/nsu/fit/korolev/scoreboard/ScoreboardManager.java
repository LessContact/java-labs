package ru.nsu.fit.korolev.scoreboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardManager {
//    public ScoreboardRecord record;

    private static final String FILE_NAME = "scoreboard_records.txt";

    public static void addRecord(ScoreboardRecord newRecord) throws IOException {
        if(newRecord.username.isEmpty()){
            return;
        }
        Path filePath = Path.of(FILE_NAME);
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
//            Files.write(filePath, Collections.singletonList(record.toString()));
        }
        List<String> lines = Files.readAllLines(filePath);
        lines.add(newRecord.getUsername() + ":" + newRecord.getScore());

        List<ScoreboardRecord> records = new ArrayList<>();
        for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length > 1) {
                    String username = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    ScoreboardRecord record = new ScoreboardRecord(username, score, false);
                    if(parts.length > 2){
                        record.setTampered(true);
                    }
                    records.add(record);
                }
            }
        records.sort(Comparator.comparingInt(ScoreboardRecord::getScore).reversed());
        lines.clear();
        for (ScoreboardRecord record : records) {
                String entry = record.username + ": " + record.score;
                if(record.isTampered()) entry += " : (tampered)";
                lines.add(entry);
            }
        int newHash = lines.hashCode();
        lines.addFirst(Integer.toString(newHash));

        Files.write(filePath, lines);
    }

    public static List<String> readRecords() throws IOException {
        Path filePath = Paths.get(FILE_NAME);
        if (Files.exists(filePath)) {
            List<String> lines = Files.readAllLines(filePath);

            String hash = lines.getFirst();
            lines.removeFirst();
            int newHash = lines.hashCode();
            boolean hasBeenChanged = Integer.parseInt(hash) != newHash;

            List<String> records = new ArrayList<>();
            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length > 1) {
                    String username = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    String record = username + ": " + score;
                    if(parts.length > 2){
                        record += " : (tampered)";
                    }
                    else if(hasBeenChanged){
                        String isTampered = " : (tampered)";
                        record += isTampered;
                    }

                    records.add(record);
                }
            }
            records.addFirst(Integer.toString(newHash));
            Files.write(filePath, records);
            records.removeFirst();
            return records;
        }
        return Collections.emptyList();
    }

}
