/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        UMLClass.java
 * Description: UMLClass class
 */

package ija.project.uml;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class of the class diagram.
 */
public class UMLClass extends Element {

    /**
     * List of attributes.
     */
    private final List<Attribute> attributeList = new ArrayList<>();

    /**
     * List of methods.
     */
    private final List<Method> methodList = new ArrayList<>();

    private Label nameView = null;

    private VBox attributeView = null;

    private VBox methodView = null;

    private VBox classView = null;

    private double positionX = 0.0;

    private double positionY = 0.0;

    /**
     * Creates new class.
     *
     * @param name name of class.
     */
    public UMLClass (String name) {
        super(name);
    }

    /**
     * Add new attribute to class.
     *
     * @param newAttribute new attribute.
     */
    public void addAttribute (Attribute newAttribute) {
        this.attributeList.add(newAttribute);
        if (this.attributeView != null) {
            Text attributeText = new Text();
            newAttribute.setTextView(attributeText);
            attributeText.setText(newAttribute.toString());
            this.attributeView.getChildren().add(attributeText);
        }
    }

    /**
     * Removes the selected attribute from the attribute list
     * @param attributeToRemove which attribute should be removed
     */
    public void removeAttribute (Attribute attributeToRemove) {
        if (this.attributeView != null) {
            attributeView.getChildren().remove(attributeToRemove.getTextView());
        }
        this.attributeList.remove(attributeToRemove);
    }

    /**
     * Add new method to class.
     *
     * @param newMethod new method.
     */
    public void addMethod (Method newMethod) {
        this.methodList.add(newMethod);
        if (this.methodView != null) {
            Text attributeText = new Text();
            newMethod.setTextView(attributeText);
            attributeText.setText(newMethod.toString());
            this.methodView.getChildren().add(attributeText);
        }
    }

    /**
     * Removes the selected method from the method list
     * @param methodToRemove which method should be removed
     */
    public void removeMethod (Method methodToRemove) {
        if (this.methodView != null) {
            methodView.getChildren().remove(methodToRemove.getTextView());
        }
        this.methodList.remove(methodToRemove);
    }

    /**
     * Find attribute in class.
     *
     * @param attributeName name of attribute.
     * @return instance of attribute or null.
     */
    public Attribute findAttribute (String attributeName) {
        return this.attributeList.stream().
                filter(U -> U.getName().equals(attributeName)).
                findFirst().
                orElse(null);
    }

    /**
     * Find method in class.
     *
     * @param methodName name of method.
     * @return instance of method or null.
     */
    public Method findMethod (String methodName) {
        return this.methodList.stream().
                filter(U -> U.getName().equals(methodName)).
                findFirst().
                orElse(null);
    }

    /**
     * Getter for the attribute list.
     * @return The attribute list
     */
    public List<Attribute> getAttributeList () {
        return Collections.unmodifiableList(this.attributeList);
    }

    /**
     * Getter for the method list,
     * @return The method list
     */
    public List<Method> getMethodList () {
        return Collections.unmodifiableList(this.methodList);
    }

    /**
     * Setter for the nameView - creates the label representation of the selected name
     * @param nameView selected name
     */
    public void setNameView (Label nameView) {
        this.nameView = nameView;
        this.nameView.setText(this.name);
    }

    /**
     * Setter for the attribute view - attribute view is the VBox representation
     * @param attributeView attribute view (VBox)
     */
    public void setAttributeView (VBox attributeView) {
        this.attributeView = attributeView;
    }

    /**
     * Setter for the method view - method view is the VBox representation
     * @param methodView method view (VBox)
     */
    public void setMethodView (VBox methodView) {
        this.methodView = methodView;
    }

    /**
     * Setter for the class view
     * @param classView new class view
     */
    public void setClassView (VBox classView) {
        this.classView = classView;
    }

    /**
     * Getter for the class view
     * @return the class view
     */
    public VBox getClassView () {
        return this.classView;
    }

    /**
     * Setter for the X position
     * @param positionX new X position
     */
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    /**
     * Setter for the Y position
     * @param positionY new Y position
     */
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    /**
     * Getter for the X position
     * @return X position
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     * Getter for the Y position
     * @return Y position
     */
    public double getPositionY() {
        return positionY;
    }

    /**
     * Setter for the name
     * @param newName new name.
     */
    @Override
    public void setName (String newName) {
        super.setName(newName);
        if (nameView != null) {
            this.nameView.setText(newName);
        }
    }
}