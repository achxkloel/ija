/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        UMLRelation.java
 * Description: Relation class
 */

package ija.project.uml;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

import java.util.Objects;

/**
 * Relation between classes.
 */
public class UMLRelation extends Element {

    private String type;
    private String source;
    private String target;
    private final String cardinalityFrom;
    private final String cardinalityTo;
    private VBox vboxFrom;
    private VBox vboxTo;
    private Line line;
    private String lineOrientation;
    private Polygon polygon = null;

    private Polyline polyline = null;

    public UMLRelation (String name, String type, String source, String target, String cardinalityFrom, String cardinalityTo) {
        super(name);
        this.type = type;
        this.source = source;
        this.target = target;
        this.cardinalityFrom = cardinalityFrom;
        this.cardinalityTo = cardinalityTo;
    }

    public void setType(String newType) {
        this.type = newType;
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

    public Polygon getPolygon() {
        return polygon;
    }

    public Node getArrow() {
        if (this.type.equals("association")) {
            return this.polyline;
        } else {
            return this.polygon;
        }
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

        Point2D point = getIntersectionPoint();

        if (Objects.equals(type, "aggregation") || Objects.equals(type, "composition")) {
            polygon = new Polygon();
            polygon.setStroke(Color.BLACK);
            if (!Objects.equals(type, "composition"))
                polygon.setFill(Color.TRANSPARENT);

            if (Objects.equals(lineOrientation, "top")) {
                polygon.getPoints().addAll(point.getX(), point.getY(),
                        point.getX() + 10, point.getY() - 10,
                        point.getX(), point.getY() - 20,
                        point.getX() - 10, point.getY() - 10);
                line.setEndX(point.getX());
                line.setEndY(point.getY() - 20);
            } else if (Objects.equals(lineOrientation, "bottom")) {
                polygon.getPoints().addAll(point.getX(), point.getY(),
                        point.getX() + 10, point.getY() + 10,
                        point.getX(), point.getY() + 20,
                        point.getX() - 10, point.getY() + 10);
                line.setEndX(point.getX());
                line.setEndY(point.getY() + 20);
            } else if (Objects.equals(lineOrientation, "left")) {
                polygon.getPoints().addAll(point.getX(), point.getY(),
                        point.getX() - 10, point.getY() - 10,
                        point.getX() - 20, point.getY(),
                        point.getX() - 10, point.getY() + 10);
                line.setEndX(point.getX() - 20);
                line.setEndY(point.getY());
            } else if (Objects.equals(lineOrientation, "right")) {
                polygon.getPoints().addAll(point.getX(), point.getY(),
                        point.getX() + 10, point.getY() - 10,
                        point.getX() + 20, point.getY(),
                        point.getX() + 10, point.getY() + 10);
                line.setEndX(point.getX() + 20);
                line.setEndY(point.getY());
            }
        } else if (Objects.equals(type, "generalization")) {
            polygon = new Polygon();
            polygon.setStroke(Color.BLACK);
            polygon.setFill(Color.TRANSPARENT);

            if (Objects.equals(lineOrientation, "top")) {
                polygon.getPoints().addAll(point.getX(), point.getY(),
                        point.getX() + 10, point.getY() - 10,
                        point.getX() - 10, point.getY() - 10);
                line.setEndX(point.getX());
                line.setEndY(point.getY() - 10);
            } else if (Objects.equals(lineOrientation, "bottom")) {
                polygon.getPoints().addAll(point.getX(), point.getY(),
                        point.getX() + 10, point.getY() + 10,
                        point.getX() - 10, point.getY() + 10);
                line.setEndX(point.getX());
                line.setEndY(point.getY() + 10);
            } else if (Objects.equals(lineOrientation, "left")) {
                polygon.getPoints().addAll(point.getX(), point.getY(),
                        point.getX() - 10, point.getY() - 10,
                        point.getX() - 10, point.getY() + 10);
                line.setEndX(point.getX() - 10);
                line.setEndY(point.getY());
            } else if (Objects.equals(lineOrientation, "right")) {
                polygon.getPoints().addAll(point.getX(), point.getY(),
                        point.getX() + 10, point.getY() - 10,
                        point.getX() + 10, point.getY() + 10);
                line.setEndX(point.getX() + 10);
                line.setEndY(point.getY());
            }
        } else if (Objects.equals(type, "__association__")) { // change to "association" to enable this type
            polyline = new Polyline();
            polyline.setStroke(Color.BLACK);

            if (Objects.equals(lineOrientation, "top")) {
                polyline.getPoints().addAll(point.getX() + 10, point.getY() - 10,
                        point.getX(), point.getY(),
                        point.getX() - 10, point.getY() - 10);
                line.setEndX(point.getX());
                line.setEndY(point.getY());
            } else if (Objects.equals(lineOrientation, "bottom")) {
                polyline.getPoints().addAll(point.getX() + 10, point.getY() + 10,
                        point.getX(), point.getY(),
                        point.getX() - 10, point.getY() + 10);
                line.setEndX(point.getX());
                line.setEndY(point.getY() + 10);
            } else if (Objects.equals(lineOrientation, "left")) {
                polyline.getPoints().addAll(point.getX() - 10, point.getY() - 10,
                        point.getX(), point.getY(),
                        point.getX() - 10, point.getY() + 10);
                line.setEndX(point.getX() - 10);
                line.setEndY(point.getY());
            } else if (Objects.equals(lineOrientation, "right")) {
                polyline.getPoints().addAll(point.getX() + 10, point.getY() - 10,
                        point.getX(), point.getY(),
                        point.getX() + 10, point.getY() + 10);
                line.setEndX(point.getX() + 10);
                line.setEndY(point.getY());
            }
        }
    }

    public Point2D getIntersectionPoint() {
        Point2D point = new Point2D(
                vboxTo.getLayoutX() + vboxTo.getTranslateX(),
                vboxTo.getLayoutY() + vboxTo.getTranslateY()
        );

        for (double x = 0; x < vboxTo.getWidth(); x++) {
            if (line.contains(point)) {
                lineOrientation = "top";
                return point;
            }
            point = point.add(0, vboxTo.getHeight());
            if (line.contains(point)) {
                lineOrientation = "bottom";
                return point;
            }
            point = point.add(1, -vboxTo.getHeight());
        }

        point = new Point2D(
                vboxTo.getLayoutX() + vboxTo.getTranslateX(),
                vboxTo.getLayoutY() + vboxTo.getTranslateY()
        );

        for (double y = 0; y < vboxTo.getHeight(); y++) {
            if (line.contains(point)) {
                lineOrientation = "left";
                return point;
            }
            point = point.add(vboxTo.getWidth(), 0);
            if (line.contains(point)) {
                lineOrientation = "right";
                return point;
            }
            point = point.add(-vboxTo.getWidth(), 1);
        }

        return point;
    }
}
