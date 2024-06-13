package ru.nsu.fit.korolev.controller;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.korolev.model.GameModel;
import ru.nsu.fit.korolev.model.Player;
import ru.nsu.fit.korolev.view.GameField;
import ru.nsu.fit.korolev.view.MainMenu;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.security.PublicKey;


public class GameController extends Application {
    @Getter
    private static final GameController instance = new GameController();
    private Image icon = new Image("logo.png");
//    private Parent root;
    private Parent root;
    private Stage stage;
    @Getter
    @Setter
    boolean isStopped = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance.stage = primaryStage;
        primaryStage.setTitle("Distinct Game");
        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);
        root = FXMLLoader.load(ClassLoader.getSystemResource("MainMenu.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }

    public void SetGamePlayScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("GameField.fxml"));
//        root = loader.load(ClassLoader.getSystemResource("GameField.fxml"));
        root = loader.load();
        GameField gameField = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Player player = new Player();
        GameModel model = new GameModel(gameField.getField(), gameField.getScore(), player);
        MapKeys(scene, model);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(!isStopped) {

                    try {
                        model.update();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
//                view.draw();
            }
        };

        timer.start();
//        while (!isStopped) {}
//        timer.stop();
//        stage.setScene(scene);
    }

    public void SetScoreBoardScene() throws IOException {
        root = FXMLLoader.load(ClassLoader.getSystemResource("ScoreBoard.fxml"));
        stage.setScene(new Scene(root));
    }

    public void SetMainMenuScene() throws IOException {
        root = FXMLLoader.load(ClassLoader.getSystemResource("MainMenu.fxml"));
        stage.setScene(new Scene(root));
    }
    public void SetFailScene() throws IOException {
        root = FXMLLoader.load(ClassLoader.getSystemResource("Fail.fxml"));
        stage.setScene(new Scene(root));
    }

//    private void MapKeys(Scene scene, Player player) {
    private void MapKeys(Scene scene, GameModel model) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    model.getPlayer().setXacc(-0.02);
                    break;
                case RIGHT:
                    model.getPlayer().setXacc(0.02);
                    break;
//                case UP:
//                    player.shoot();
            }
        });
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    model.getPlayer().setXacc(0);
                    break;
                case RIGHT:
                    model.getPlayer().setXacc(0);
                    break;
            }
        });
    }
}
