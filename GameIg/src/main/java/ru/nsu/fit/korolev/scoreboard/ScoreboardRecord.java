package ru.nsu.fit.korolev.scoreboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreboardRecord {
    String username = "";
    int score = 0;
    boolean Tampered = false;

    public ScoreboardRecord(String username, int score, boolean tampered) {
        this.username = username;
        this.score = score;
        Tampered = tampered;
    }
}
