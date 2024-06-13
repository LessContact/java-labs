package ru.nsu.fit.korolev.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ru.nsu.fit.korolev.controller.GameController;

import java.io.IOException;

public class ScoreBoard {
    @FXML
    private Button back;

//    @FXML
//    private void BackButtonAction(ActionEvent event) throws IOException {
//        GameController.getInstance().SetMainMenuScene();
//    }
    @FXML
    private void Back(ActionEvent event) throws IOException {
        GameController.getInstance().SetMainMenuScene();
    }
}
