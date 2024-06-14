package ru.nsu.fit.korolev;

import ru.nsu.fit.korolev.controller.GameController;

import java.util.ArrayList;

import static javafx.application.Application.launch;

public class Launcher {

    public static void main(String[] args) {
        launch(GameController.class, args);
        ArrayList<Integer> a = new ArrayList<Integer>();
    }

}