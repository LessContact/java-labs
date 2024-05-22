package ru.nsu.fit.korolev.Game;

import javax.swing.*;

public class JumperGame extends JFrame {

    private GameModel GameModel = null;
    private GameView GameView = null;
    private GameController GameController = null;

    public JumperGame() {
        setTitle("Distinct Jump Game");
        setSize(600, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        GameModel = new GameModel();
        GameController = new GameController(GameModel);
        GameView = new GameView(GameModel, GameController, this);

        setVisible(true);
        pack();
    }

}
