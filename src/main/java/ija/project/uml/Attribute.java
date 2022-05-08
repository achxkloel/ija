/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        Attribute.java
 * Description: Attribute class
 */

package ija.project.uml;

import javafx.scene.text.Text;

/**
 * Attribute of the class
 */
public class Attribute extends Element {

    protected String type;

    private String visibility = "";

    private Text textView = null;

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

    public void setType (String newType) {
        this.type = newType;
    }

    public String getVisibility () {
        return this.visibility;
    }

    public void setVisibility (String newVisibility) {
        this.visibility = newVisibility;
    }

    public void setTextView (Text textView) {
        this.textView = textView;
    }

    public Text getTextView () {
        return this.textView;
    }

    public void updateTextView () {
        if (this.textView != null) {
            this.textView.setText(this.toString());
        }
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
