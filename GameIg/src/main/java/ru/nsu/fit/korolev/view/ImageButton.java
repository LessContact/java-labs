package ru.nsu.fit.korolev.view;

import javax.swing.*;
import java.awt.*;


class ImageButton extends JButton {
    private Image image;

//    public ImageButton(Path path) {
//        super();
//        ImageIcon icon = new ImageIcon(path.toString());
//        image = icon.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
//        setBorderPainted(false);
//        setFocusPainted(false);
//        setContentAreaFilled(false);
//    }

    public ImageButton(String path) {
        super();
        ImageIcon icon = new ImageIcon(path);
        image = icon.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return image == null ? super.getPreferredSize() : new Dimension(image.getWidth(this), image.getHeight(this));
    }

}