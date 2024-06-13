package ru.nsu.fit.korolev.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import ru.nsu.fit.korolev.controller.GameController;

import java.io.IOException;

public class MainMenu {
    @FXML
    private Text gameName;
    @FXML
    private Button playButton;
    @FXML
    private Button scoreButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView imageView;

    @FXML
    private void PlayButtonAction(ActionEvent event) throws IOException {
        GameController.getInstance().SetGamePlayScene();
    }
    @FXML
    private void ScoreButtonAction(ActionEvent event) throws IOException {
        GameController.getInstance().SetScoreBoardScene();
    }
}
