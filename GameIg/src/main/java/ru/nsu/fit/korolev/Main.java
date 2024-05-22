package ru.nsu.fit.korolev;

import ru.nsu.fit.korolev.Game.JumperGame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(JumperGame::new);
    }
}