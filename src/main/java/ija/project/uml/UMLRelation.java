/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        UMLRelation.java
 * Description: Relation class
 */

package ija.project.uml;

import javafx.scene.layout.VBox;

/**
 * Relation between classes.
 */
public class UMLRelation extends Element {

    private final String type;
    private final String source;
    private final String target;
    private final String cardinalityFrom;
    private final String cardinalityTo;
    private VBox vboxFrom;
    private VBox vboxTo;

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

    public void setVboxFrom(VBox vboxFrom) {
        this.vboxFrom = vboxFrom;
    }

    public void setVboxTo(VBox vboxTo) {
        this.vboxTo = vboxTo;
    }
}
