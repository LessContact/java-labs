package ru.nsu.fit.korolev.model;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Player extends Entity{

    public double Xacc = 0;
    public double Yacc = 0.01; //todo
//    public static final double Yacc = 0.0; //todo
    public static final double GRAVITY = 0.01;
    public static final double SpeedSetOnJump = -1.5;
    public static final double MAX_JUMP_HEIGHT = SpeedSetOnJump /(GRAVITY);
    public static final double PLAYER_WIDTH = 17;

    public Player() {
        this.setYvel(-1);
    }
    public Player(Node view){
        super(view);
    }
    @Override
    public void update(){
        double newX = ((getView().getTranslateX() + getXvel()) % (getView().getScene().getWidth() - PLAYER_WIDTH));
        if(newX < 0) newX = getView().getScene().getWidth() - PLAYER_WIDTH;
        double newY = getView().getTranslateY() + getYvel();
        getView().setTranslateX(newX);
        if(newY < getView().getScene().getHeight()/2){
        }
        else getView().setTranslateY(newY);
    }
    public void updateVelocity(){
        if(Xacc == 0){
//            while (getXvel() != 0 && Xacc == 0) {
                setXvel(getXvel() / 2);
//            }
        }
        setXvel(Xacc + getXvel());
        if(getXvel() > 1){
            setXvel(1);
        }
        else if(getXvel() < -1){
            setXvel(-1);
        }
        setYvel(Yacc + getYvel());
        if(getYvel() > 1){
            setYvel(1);
        }
    }
}
