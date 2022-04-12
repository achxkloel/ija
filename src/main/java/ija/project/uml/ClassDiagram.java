package ija.project.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * Class diagram.
 */
public class ClassDiagram extends Element {
    /**
     * List of classes.
     */
    private final List<UMLClass> classList = new ArrayList<>();

    /**
     * List of relations.
     */
    private final List<UMLRelation> relationList = new ArrayList<>();

    /**
     * Creates class diagram.
     *
     * @param name name of class diagram.
     */
    public ClassDiagram (String name) {
        super(name);
    }

    /**
     * Add new class to the diagram.
     *
     * @param newClass new class.
     */
    public void addClass (UMLClass newClass) {
        this.classList.add(newClass);
    }

    /**
     * Add new relation to the diagram.
     *
     * @param newRelation new relation.
     */
    public void addRelation (UMLRelation newRelation) {
        this.relationList.add(newRelation);
    }

    /**
     * Find class by class name.
     *
     * @param UMLClassName name of class
     * @return instance of class or null.
     */
    public UMLClass findClass (String UMLClassName) {
        return this.classList.stream().
                filter(U -> U.getName().equals(UMLClassName)).
                findFirst().
                orElse(null);
    }
}
