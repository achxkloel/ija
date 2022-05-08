/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        ClassDiagram.java
 * Description: Class diagram class
 */

package ija.project.uml;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
     * Diagram view.
     */
    private Pane diagramView = null;

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
     * Remove class from the diagram.
     *
     * @param classToRemove class to remove.
     */
    public void removeClass (UMLClass classToRemove) {
        if (diagramView != null) {
            diagramView.getChildren().remove(classToRemove.getClassView());
        }
        this.classList.remove(classToRemove);
    }

    /**
     * Remove relation from the diagram.
     *
     * @param relationToRemove relation to remove.
     */
    public void removeRelation (UMLRelation relationToRemove) {
        if (diagramView != null) {
            diagramView.getChildren().remove(relationToRemove.getLine());
            diagramView.getChildren().remove(relationToRemove.getPolygon());
        }
        this.relationList.remove(relationToRemove);
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

    /**
     * Find relation by it source and target.
     *
     * @param source name of source.
     * @param target name of target.
     * @return instance of relation or null.
     */
    public UMLRelation findRelation (String source, String target) {
        return this.relationList.stream().
                filter(R ->
                    R.getSource().equals(source) && R.getTarget().equals(target) ||
                    R.getSource().equals(target) && R.getTarget().equals(source)).
                findFirst().
                orElse(null);
    }

    /**
     * Find relations for class name.
     *
     * @param className name of the class.
     * @return list of found relations.
     */
    public List<UMLRelation> findRelationsForClass (String className) {
        return this.relationList.stream()
                .filter(R ->
                    R.getSource().equals(className) || R.getTarget().equals(className))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Get unmodifiable class list.
     *
     * @return unmodifiable class list.
     */
    public List<UMLClass> getClassList () {
        return Collections.unmodifiableList(this.classList);
    }

    /**
     * Get unmodifiable relation list.
     *
     * @return unmodifiable relation list.
     */
    public List<UMLRelation> getRelationList() {
        return relationList;
    }

    /**
     * Clear class diagram.
     */
    public void clear() {
        this.classList.clear();
        this.relationList.clear();
    }

    /**
     * Set the class diagram view.
     *
     * @param diagramView diagram view.
     */
    public void setDiagramView (Pane diagramView) {
        this.diagramView = diagramView;
    }

    /**
     * Get the class diagram view.
     *
     * @return diagram view.
     */
    public Pane getDiagramView () {
        return this.diagramView;
    }
}
