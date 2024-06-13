package ru.nsu.fit.korolev.model;

import javafx.scene.Node;

public class My_Platform extends Entity {
    public static final double WIDTH = 35.0;
    public static final double HEIGHT = 7.0;
    public boolean broken;

    My_Platform(Node view, boolean broken){
        super(view);
        this.broken = broken;
//        setVisible(true);
    }
}
