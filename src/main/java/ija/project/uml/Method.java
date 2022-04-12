/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        Method.java
 * Description: Method class
 */

package ija.project.uml;

import java.util.ArrayList;
import java.util.List;

public class Method extends Attribute {

    /**
     * Return type of method
     */
    private final String returnType;
    /**
     * List of attributes of method
     */
    private final List<Attribute> attributeList = new ArrayList<>();

    /**
     * Creates new method
     * @param name name of method
     */
    public Method (String name, String type, String returnType) {
        super(name, type);
        this.returnType = returnType;
    }

    /**
     * Get return type of Method
     * @return return type of method
     */
    public String getReturnType () {
        return returnType;
    }

    /**
     * Add new attribute to method.
     *
     * @param newAttribute new attribute
     */
    public void addAttribute (Attribute newAttribute) {
        attributeList.add(newAttribute);
    }

    @Override
    public String toString () {
        return  "name: " + this.getName() +
                ", type: " + this.returnType + "\n\t" +
                "params:\n\t\t" + this.attributeList.get(0).toString();
    }
}
