package ru.nsu.fit.korolev.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import ru.nsu.fit.korolev.controller.GameController;
import ru.nsu.fit.korolev.scoreboard.ScoreboardRecord;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScoreBoard implements Initializable {
    @FXML
    private Button back;

    @FXML
    ListView<String> records;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        records.setItems(GameController.getInstance().getRecords());
    }

    @FXML
    private void Back(ActionEvent event) throws IOException {
        GameController.getInstance().SetMainMenuScene();
    }
}
