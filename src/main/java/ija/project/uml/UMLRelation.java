package ija.project.uml;

public class UMLRelation extends Element {

    private final String type;
    private final UMLClass source;
    private final UMLClass target;
    private final String description;
    private final String cardinalityFrom;
    private final String cardinalityTo;

    public UMLRelation (String name, String type, UMLClass source, UMLClass target, String description, String cardinalityFrom, String cardinalityTo) {
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

    public UMLClass getSource() {
        return source;
    }

    public UMLClass getTarget() {
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
