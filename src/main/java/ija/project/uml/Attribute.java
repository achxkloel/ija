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

    /**
     * Type of attribute.
     */
    protected String type;

    /**
     * Visibility of attribute.
     */
    private String visibility = "";

    /**
     * Attribute text view.
     */
    private Text textView = null;

    /**
     * Creates new attribute
     *
     * @param name name of attribute.
     * @param type type of attribute.
     * @param visibility visibility of attribute.
     */
    public Attribute (String name, String type, String visibility) {
        super(name);
        this.type = type;
        this.visibility = visibility;
    }

    /**
     * Creates a new attribute without visibility.
     *
     * @param name name of attribute.
     * @param type type of attribute.
     */
    public Attribute (String name, String type) {
        super(name);
        this.type = type;
    }

    /**
     * Get the attribute type.
     *
     * @return attribute type.
     */
    public String getType () {
        return type;
    }

    /**
     * Set the attribute type.
     *
     * @param newType new attribute type.
     */
    public void setType (String newType) {
        this.type = newType;
    }

    /**
     * Get the attribute visibility.
     *
     * @return attribute visibility.
     */
    public String getVisibility () {
        return this.visibility;
    }

    /**
     * Set the attribute visibility.
     *
     * @param newVisibility new attribute visibility.
     */
    public void setVisibility (String newVisibility) {
        this.visibility = newVisibility;
    }

    /**
     * Set the attribute text view.
     *
     * @param textView new attribute text view.
     */
    public void setTextView (Text textView) {
        this.textView = textView;
    }

    /**
     * Get the attribute text view.
     *
     * @return attribute text view.
     */
    public Text getTextView () {
        return this.textView;
    }

    /**
     * Update the attribute text view.
     */
    public void updateTextView () {
        if (this.textView != null) {
            this.textView.setText(this.toString());
        }
    }

    /**
     * Get visibility symbol fro string.
     *
     * @return special visibility symbol (as string).
     */
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

    /**
     * Override toString method.
     *
     * @return attribute string.
     */
    @Override
    public String toString () {
        return this.getVisibilityChar() + " " + this.name + " : " + this.type;
    }
}
