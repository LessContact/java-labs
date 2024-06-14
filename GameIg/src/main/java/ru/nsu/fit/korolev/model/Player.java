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
    public static final double SPEED_SET_ON_JUMP = -1.5;
    public static final double MAX_JUMP_HEIGHT = SPEED_SET_ON_JUMP * SPEED_SET_ON_JUMP /(GRAVITY);
    public static final double PLAYER_HITBOX_WIDTH = 17;
    public static final double PLAYER_HITBOX_HEIGHT = 4;
    public static final double PLAYER_SPRITE_SIZE = PLAYER_HITBOX_WIDTH + 3;

    public Player() {
        this.setYvel(SPEED_SET_ON_JUMP);
    }
    public Player(Node view){
        super(view);
    }
    @Override
    public void update(){

        double newX = ((getXcoord() + getXvel()) % (getView().getScene().getWidth() - PLAYER_HITBOX_WIDTH));
        if(newX < 0) newX = getView().getScene().getWidth() - PLAYER_HITBOX_WIDTH;
        setXcoord(newX);

        double newY = getYcoord() + getYvel();
        if(newY < getView().getScene().getHeight()/2){
        }
        else setYcoord(newY);
    }

    public void updateVelocity(){
        if(Xacc == 0){
                setXvel(getXvel() / 2);
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
