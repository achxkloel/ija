/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        UMLRelation.java
 * Description: Relation class
 */

package ija.project.uml;

public class UMLRelation extends Element {

    private final String type;
    private final String source;
    private final String target;
    private final String description;
    private final String cardinalityFrom;
    private final String cardinalityTo;

    public UMLRelation (String name, String type, String source, String target, String description, String cardinalityFrom, String cardinalityTo) {
        super(name);
        this.type = type;
        this.source = source;
        this.target = target;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public String getCardinalityFrom() {
        return cardinalityFrom;
    }

    public String getCardinalityTo() {
        return cardinalityTo;
    }
}
