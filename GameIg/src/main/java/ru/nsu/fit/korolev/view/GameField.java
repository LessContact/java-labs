package ru.nsu.fit.korolev.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lombok.Getter;
import ru.nsu.fit.korolev.controller.GameController;

import java.io.IOException;

public class GameField {
    @Getter
    @FXML
    private Label score;
    @Getter
    @FXML
    private Pane field;


}
