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

}
