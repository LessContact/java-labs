package ru.nsu.fit.korolev.model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import ru.nsu.fit.korolev.controller.GameController;
import ru.nsu.fit.korolev.view.GameField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameModel {
    public ArrayList<My_Platform> platforms;

    private Pane field;
    private Label scoreLabel;
    private GameField gameFieldScene;
    @Getter
    private Player player;
    @Getter
    private Integer score = 0;

    private double difficulty = 10.0;
    private double minPlatformGap = My_Platform.HEIGHT + Player.MAX_JUMP_HEIGHT / difficulty;
    private double maxPlatformGap = (Player.MAX_JUMP_HEIGHT - 10) / difficulty;
    private Random rand = new Random();
    private Image platformNorm = new Image("platform-norm.png");
    private Image platformBroken = new Image("broken.png");
    private Image platformMove = new Image("platform-move.png");

    //    public GameModel(Node pane) {
    public GameModel(Pane field, Label scoreLabel, Player player, GameField gameFieldScene) {
        platforms = new ArrayList<>();
        this.field = field;
        this.scoreLabel = scoreLabel;
        this.gameFieldScene = gameFieldScene;

        double startPlatformYPos = field.getHeight(); // Start at the bottom of the screen

        Rectangle rect = new Rectangle(0, 0,Player.PLAYER_HITBOX_WIDTH, Player.PLAYER_HITBOX_HEIGHT);
        double startXPos = field.getWidth() / 2;
        double startYPos = field.getHeight() / 1.5;
        rect.setTranslateX(startXPos);
        rect.setTranslateY(startYPos);

        rect.setFill(Color.TRANSPARENT);
        player.setView(rect);
        player.setXcoord(startXPos);
        player.setYcoord(startYPos);
        this.player = player;

        field.getChildren().add(this.player.getView());

        generatePlatforms(startPlatformYPos);
    }

    private double getRandomGap() {
        return rand.nextDouble() * (maxPlatformGap - minPlatformGap) + minPlatformGap;
    }

    private void generatePlatforms(double startYPos) {
        double currentYPosition = startYPos;
//        if (!platforms.isEmpty() && startYPos > platforms.getLast().getView().getTranslateY()){
        if (!platforms.isEmpty() && startYPos > platforms.getLast().getYcoord()){
            return;
        }
        ArrayList<My_Platform> platforms = new ArrayList<>();

        while(currentYPosition > 0){
            double platformXPosition = (rand.nextInt((int)field.getBoundsInParent().getWidth()) * (My_Platform.WIDTH + getRandomGap())) % (field.getBoundsInParent().getWidth());
            boolean isBroken = rand.nextDouble() > 0.9;
            Rectangle rect = new Rectangle(platformXPosition, currentYPosition, My_Platform.WIDTH, My_Platform.HEIGHT);

            if(isBroken && getScore() < 1200){
                rect.setFill(new ImagePattern(platformBroken));
                platforms.add(new My_Platform(rect, true, platformXPosition, currentYPosition));
                currentYPosition -= My_Platform.HEIGHT + 5;
            }
            else if(rand.nextDouble() > 0.5 && getScore() > 1000){
                rect.setFill(new ImagePattern(platformMove));
                platforms.add(new My_Platform(rect, false, platformXPosition, currentYPosition));
                platforms.getLast().setXvel(0.33);
                currentYPosition -= My_Platform.HEIGHT + getRandomGap();
            }
            else{
                rect.setFill(new ImagePattern(platformNorm));
                platforms.add(new My_Platform(rect, false, platformXPosition, currentYPosition));
                currentYPosition -= My_Platform.HEIGHT + getRandomGap();
            }

        }
        for (My_Platform platform : platforms) {
            field.getChildren().add(platform.getView());
        }
        this.platforms.addAll(platforms);
    }

    private void checkVisibility(Entity entity){

        boolean isVisible = entity.getView().getBoundsInParent().intersects(field.getLayoutBounds())
                || field.getLayoutBounds().contains(entity.getView().getBoundsInParent());

        if(!isVisible){
            field.getChildren().remove(entity.getView());
            entity.setVisible(false);
        }
    }

    public void update() throws IOException {

        for(My_Platform platform : platforms){

            if(player.getYvel() > 0 && player.isColliding(platform) && !platform.broken){
                player.setYvel(Player.SpeedSetOnJump);
                break;
            }else if(player.getYvel() > 0 && player.isColliding(platform) && platform.broken){
                field.getChildren().remove(platform.getView());
                platform.setVisible(false);
            }

            platform.update();
            if(Math.abs(platform.getView().getTranslateX()) > 100){
                platform.setXvel(-platform.getXvel());
            }
        }

        player.updateVelocity();

        if(player.getView().getTranslateY() + player.getYvel() < field.getScene().getHeight()/2) {

            score += Math.max((int) -player.getYvel(), 0);

            for (My_Platform platform : platforms) {
                platform.getView().setTranslateY(platform.getView().getTranslateY() - player.getYvel());

                checkVisibility(platform);
            }
            platforms.removeIf(element -> !element.isVisible());
            generatePlatforms(-player.getYvel() + minPlatformGap);
//            generatePlatforms(-player.getYvel());

        }

        player.update();
        gameFieldScene.drawPlayer(player);

        if(player.getView().getTranslateY() > field.getScene().getHeight()){
            GameController.getInstance().setStopped(true);
        }

        difficulty = Math.max(10.0 - (score / 200), 2.0);
        minPlatformGap = My_Platform.HEIGHT + Player.MAX_JUMP_HEIGHT / difficulty - 20;
        maxPlatformGap = (Player.MAX_JUMP_HEIGHT - 80) / difficulty;

        //temp

        scoreLabel.setText(score.toString());

    }

}
