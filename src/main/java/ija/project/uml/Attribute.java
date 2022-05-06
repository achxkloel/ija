/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        Attribute.java
 * Description: Attribute class
 */

package ija.project.uml;

/**
 * Attribute of the class
 */
public class Attribute extends Element {

    protected final String type;

    private String visibility = "";

    /**
     * Creates new attribute
     * @param name name of attribute
     */
    public Attribute (String name, String type, String visibility) {
        super(name);
        this.type = type;
        this.visibility = visibility;
    }

    public Attribute (String name, String type) {
        super(name);
        this.type = type;
    }

    public String getType () {
        return type;
    }

    protected String getVisibilityChar () {
        switch (this.visibility) {
            case "public":
                return "+";
            case "protected":
                return "#";
            case "private":
                return "-";
            case "package private":
                return "~";
            default:
                return "?";
        }
    }

    @Override
    public String toString () {
        return this.getVisibilityChar() + " " + this.name + " : " + this.type;
    }
}
