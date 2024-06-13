package ru.nsu.fit.korolev.model;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Entity {
    private Node View;
    private double Xcoord;
    private double Ycoord;

    private double Xvel = 0;
    private double Yvel = 0;
    private boolean isVisible = true;

    public Entity() {}
    public Entity(Node view) {
        this.View = view;
    }

    public void update(){
        View.setTranslateX(View.getTranslateX() + Xvel);
        View.setTranslateY(View.getTranslateY() + Yvel);
    }
    public void setVisible(boolean visibility){
        this.isVisible = visibility;
    }

    public boolean isColliding(Entity other){
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }



}
