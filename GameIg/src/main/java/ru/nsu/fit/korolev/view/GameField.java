package ru.nsu.fit.korolev.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import ru.nsu.fit.korolev.model.My_Platform;
import ru.nsu.fit.korolev.model.Player;

import java.net.URL;
import java.util.ResourceBundle;

public class GameField implements Initializable {
    @Getter
    @FXML
    private Label score;
    @Getter
    @FXML
    private Pane field;
    private Image playerImage = new Image("player.png");
    private Rectangle playerSprite = new Rectangle(0,0,Player.PLAYER_SPRITE_SIZE, Player.PLAYER_SPRITE_SIZE);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerSprite.setFill(new ImagePattern(playerImage));
        field.getChildren().add(playerSprite);
    }

    public void drawPlayer(Player player){
        playerSprite.setTranslateX(player.getView().getTranslateX());// + Player.PLAYER_HITBOX_HEIGHT);
        playerSprite.setTranslateY(player.getView().getTranslateY() - Player.PLAYER_SPRITE_SIZE + Player.PLAYER_HITBOX_HEIGHT);
    }

    public void drawPlatform(My_Platform platform){

    }

}
