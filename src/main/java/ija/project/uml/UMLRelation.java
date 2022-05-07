/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        UMLRelation.java
 * Description: Relation class
 */

package ija.project.uml;

import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

/**
 * Relation between classes.
 */
public class UMLRelation extends Element {

    private final String type;
    private String source;
    private String target;
    private final String cardinalityFrom;
    private final String cardinalityTo;
    private VBox vboxFrom;
    private VBox vboxTo;
    private Line line;

    public UMLRelation (String name, String type, String source, String target, String cardinalityFrom, String cardinalityTo) {
        super(name);
        this.type = type;
        this.source = source;
        this.target = target;
        this.cardinalityFrom = cardinalityFrom;
        this.cardinalityTo = cardinalityTo;
    }

    public String getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget (String newTarget) {
        this.target = newTarget;
    }

    public void setSource (String newSource) {
        this.source = newSource;
    }

    public String getCardinalityFrom() {
        return cardinalityFrom;
    }

    public String getCardinalityTo() {
        return cardinalityTo;
    }

    public VBox getVboxFrom() {
        return vboxFrom;
    }

    public VBox getVboxTo() {
        return vboxTo;
    }

    public Line getLine() {
        return line;
    }

    public void setVboxFrom(VBox vboxFrom) {
        this.vboxFrom = vboxFrom;
    }

    public void setVboxTo(VBox vboxTo) {
        this.vboxTo = vboxTo;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public void updateCoordinates() {
        this.line.setStartX(vboxFrom.getLayoutX() + vboxFrom.getTranslateX() + vboxFrom.getWidth() / 2);
        this.line.setStartY(vboxFrom.getLayoutY() + vboxFrom.getTranslateY() + vboxFrom.getHeight() / 2);
        this.line.setEndX(vboxTo.getLayoutX() + vboxTo.getTranslateX() + vboxTo.getWidth() / 2);
        this.line.setEndY(vboxTo.getLayoutY() + vboxTo.getTranslateY() + vboxTo.getHeight() / 2);
    }

    public Point2D getIntersectionPoint() {
        Point2D point = new Point2D(
                vboxTo.getLayoutX() + vboxTo.getTranslateX(),
                vboxTo.getLayoutY() + vboxTo.getTranslateY()
        );

        for (double x = 0; x < vboxTo.getWidth(); x++) {
            if (line.contains(point)) return point;
            point = point.add(0, vboxTo.getHeight());
            if (line.contains(point)) return point;
            point = point.add(1, -vboxTo.getHeight());
        }

        point = new Point2D(
                vboxTo.getLayoutX() + vboxTo.getTranslateX(),
                vboxTo.getLayoutY() + vboxTo.getTranslateY()
        );

        for (double y = 0; y < vboxTo.getHeight(); y++) {
            if (line.contains(point)) return point;
            point = point.add(vboxTo.getWidth(), 0);
            if (line.contains(point)) return point;
            point = point.add(-vboxTo.getWidth(), 1);
        }

        return point;
    }
}
