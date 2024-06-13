package ru.nsu.fit.korolev.model;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;

public class My_Platform extends Entity {
    public static final double WIDTH = 35.0;
    public static final double HEIGHT = 7.0;
    @Getter
    @Setter
    public boolean broken;

    My_Platform(Node view, boolean broken, double x, double y){
        super(view);
        this.broken = broken;
        setXcoord(x);
        setYcoord(y);
    }
}
