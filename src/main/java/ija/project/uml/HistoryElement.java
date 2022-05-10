package ija.project.uml;

import javafx.scene.input.MouseEvent;

public class HistoryElement {
    private final double x;
    private final double y;
    MouseEvent t;

    public HistoryElement(double x, double y, MouseEvent t) {
        this.x = x;
        this.y = y;
        this.t = t;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public MouseEvent getT() {
        return t;
    }
}
