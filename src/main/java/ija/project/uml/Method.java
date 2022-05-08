/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        Method.java
 * Description: Method class
 */

package ija.project.uml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Methods of classes.
 */
public class Method extends Attribute {

    /**
     * List of attributes of method
     */
    private final List<Attribute> attributeList = new ArrayList<>();

    /**
     * Creates new method
     * @param name name of method
     */
    public Method (String name, String type, String visibility) {
        super(name, type, visibility);
    }

    /**
     * Add new attribute to method.
     *
     * @param newAttribute new attribute
     */
    public void addAttribute (Attribute newAttribute) {
        attributeList.add(newAttribute);
    }

    public List<Attribute> getAttributeList () {
        return Collections.unmodifiableList(this.attributeList);
    }

    public void clearAttributes () {
        this.attributeList.clear();
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
                .append(this.attributeList.get(i).name)
                .append(" : ")
                .append(this.attributeList.get(i).type);

            if (i + 1 != this.attributeList.size()) {
                methodSb.append(", ");
            }
        }

        methodSb
            .append(") : ")
            .append(this.type);

        return methodSb.toString();
    }
}
