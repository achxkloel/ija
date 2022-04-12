/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        UMLClass.java
 * Description: UMLClass class
 */

package ija.project.uml;

import java.util.ArrayList;
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
    }

    /**
     * Add new method to class.
     *
     * @param newMethod new method.
     */
    public void addMethod (Method newMethod) {
        this.methodList.add(newMethod);
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
}