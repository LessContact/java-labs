package ru.nsu.fit.korolev.model;

import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import ru.nsu.fit.korolev.controller.GameController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameModel {
    public ArrayList<My_Platform> platforms;
    private Pane field;

    private Label scoreLabel;
    @Getter
    private Player player;
    private Integer score = 0;

    //    private static final double MAX_JUMP_HEIGHT = Player.SpeedAddOnJump * Player.SpeedAddOnJump /(2 * Player.Yacc); // maximum jump height
//    private static final int START_NUM_PLATFORMS = 50;
    private static final double MIN_PLATFORM_GAP = My_Platform.HEIGHT + 10;
    private double difficulty = 10.0;
    private double maxPlatformGap = (Player.MAX_JUMP_HEIGHT - 5) / difficulty;
    private Random rand = new Random();
    private Image platformNorm = new Image("platform-norm.png");
    private Image platformBroken = new Image("broken.png");
    private Image playerImage = new Image("player.png");

    //    public GameModel(Node pane) {
    public GameModel(Pane field, Label scoreLabel, Player player) {
        platforms = new ArrayList<>();
        this.field = field;
        this.scoreLabel = scoreLabel;

        double startYPos = field.getHeight(); // Start at the bottom of the screen

//        Rectangle rect = new Rectangle(field.getWidth()/2, field.getHeight()/1.5,17, 17);
        Rectangle rect = new Rectangle(0, 0,Player.PLAYER_WIDTH, 5);
        rect.setTranslateX(field.getWidth() / 2);
        rect.setTranslateY(field.getHeight() / 1.5);
//        rect.setFill(new ImagePattern(playerImage));
        rect.setFill(Color.RED);
        player.setView(rect);
        this.player = player;

        field.getChildren().add(this.player.getView());

        generatePlatforms(startYPos);
    }

    private double getRandomGap() {
//        return Math.random() * (maxPlatformGap - MIN_PLATFORM_GAP) + MIN_PLATFORM_GAP;
        return rand.nextDouble() * (maxPlatformGap - MIN_PLATFORM_GAP) + MIN_PLATFORM_GAP;
    }

    private void generatePlatforms(double startYPos) {
        double currentYPosition = startYPos;
        if (!platforms.isEmpty() && startYPos > platforms.getLast().getView().getTranslateY()){
            return;
        }
        ArrayList<My_Platform> platforms = new ArrayList<>();

        while(currentYPosition > 0){
            double platformXPosition = (rand.nextInt((int)field.getBoundsInParent().getWidth()) * (My_Platform.WIDTH + getRandomGap())) % (field.getBoundsInParent().getWidth());
            boolean isBroken = rand.nextDouble() > 0.9;
            Rectangle rect = new Rectangle(platformXPosition, currentYPosition, My_Platform.WIDTH, My_Platform.HEIGHT);

            if(isBroken){
                rect.setFill(new ImagePattern(platformBroken));
                platforms.add(new My_Platform(rect, isBroken));
                currentYPosition -= My_Platform.HEIGHT + difficulty;
            }
            else{
                rect.setFill(new ImagePattern(platformNorm));
                platforms.add(new My_Platform(rect, isBroken));
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
        }

        player.updateVelocity();
//        double platformVel = field.getScene().getHeight()/2 - player.getYvel() - player.getView().getTranslateY();

        if(player.getView().getTranslateY() + player.getYvel() < field.getScene().getHeight()/2) {

            score += Math.max((int) -player.getYvel(), 0);
//            for(Platform platform : platforms){
//                platform.getView().setTranslateY(platformVel);
//                checkVisibility(platform);
//            }


            for (My_Platform platform : platforms) {
                platform.getView().setTranslateY(platform.getView().getTranslateY() - player.getYvel());
                checkVisibility(platform);
            }
            platforms.removeIf(element -> !element.isVisible());
//            generatePlatforms(-player.getYvel() + player.getView().getTranslateY());
            generatePlatforms(-player.getYvel());

        }

        player.update();

        if(player.getView().getTranslateY() > field.getScene().getHeight()){
            GameController.getInstance().setStopped(true);
            GameController.getInstance().SetFailScene();
        }


        //temp

        scoreLabel.setText(score.toString());

    }

}
