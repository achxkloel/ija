package ija.project.uml;

public class Attribute extends Element {

    private final String type;

    /**
     * Creates new attribute
     * @param name name of attribute
     */
    public Attribute (String name, String type) {
        super(name);
        this.type = type;
    }

    public String getType () {
        return type;
    }


    @Override
    public String toString () {
        return "name: " + this.getName() + ", type: " + this.type;
    }
}
