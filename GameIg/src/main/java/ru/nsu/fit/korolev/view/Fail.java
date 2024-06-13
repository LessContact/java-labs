package ru.nsu.fit.korolev.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.nsu.fit.korolev.controller.GameController;

import java.io.IOException;


public class Fail {
    @FXML
    TextField name;
    @FXML
    Button submit;
    @FXML
    Button again;

    @FXML
    private void submitButtonAction(ActionEvent event) throws IOException {

        GameController.getInstance().setStopped(false);
        GameController.getInstance().SetMainMenuScene();
    }
    @FXML
    private void againButtonAction(ActionEvent event) throws IOException {
        GameController.getInstance().setStopped(false);
        GameController.getInstance().SetGamePlayScene();
    }
}
