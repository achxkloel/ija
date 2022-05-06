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

/**
 * Methods of classes.
 */
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
    public Method (String name, String type, String returnType, String visibility) {
        super(name, type, visibility);
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
        StringBuilder methodSb = new StringBuilder();
        methodSb
            .append(this.getVisibilityChar())
            .append(" ")
            .append(this.name)
            .append(" (");

        for (int i = 0; i < this.attributeList.size(); i++) {
            methodSb
                .append(this.name)
                .append(" : ")
                .append(this.type);

            if (i + 1 != this.attributeList.size()) {
                methodSb.append(", ");
            }
        }

        methodSb
            .append(") : ")
            .append(this.returnType);

        return methodSb.toString();
    }
}
