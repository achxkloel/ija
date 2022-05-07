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
 * Class of the diagram.
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

    public List<Attribute> getAttributeList () {
        return Collections.unmodifiableList(this.attributeList);
    }

    public List<Method> getMethodList () {
        return Collections.unmodifiableList(this.methodList);
    }

    public void setNameView (Label nameView) {
        this.nameView = nameView;
        this.nameView.setText(this.name);
    }

    public void setAttributeView (VBox attributeView) {
        this.attributeView = attributeView;
    }

    public void setMethodView (VBox methodView) {
        this.methodView = methodView;
    }

    public void setClassView (VBox classView) {
        this.classView = classView;
    }

    public VBox getClassView () {
        return this.classView;
    }

    @Override
    public void setName (String newName) {
        super.setName(newName);
        if (nameView != null) {
            this.nameView.setText(newName);
        }
    }
}