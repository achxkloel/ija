/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneEditUMLClassController.java
 * Description: Controller for Edit UML Class window.
 */

package ija.project.uml.controllers;

import ija.project.uml.ClassDiagram;
import ija.project.uml.UMLClass;
import ija.project.uml.UMLRelation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

/**
 * Edit UML Class controller.
 */
public class SceneEditUMLClassController {

    /**
     * Save button.
     */
    @FXML
    Button saveButton;

    /**
     * Delete button.
     */
    @FXML
    Button deleteButton;

    /**
     * Name text field.
     */
    @FXML
    TextField nameTextField;

    /**
     * Parent diagram.
     */
    ClassDiagram parentDiagram;

    /**
     * Edited UML Class.
     */
    UMLClass editedClass;

    /**
     * Controller initialization.
     */
    @FXML
    public void initialize () {
        // on Save button click
        saveButton.setOnAction(event -> {
            String newName = nameTextField.getText().trim();

            if (newName.isEmpty()) {
                System.out.println("Text field is empty!");
                return;
            }

            if (!newName.equals(editedClass.getName()) &&
                parentDiagram.findClass(newName) != null) {
                System.out.println("Class \"" + newName + "\" is already exists!");
                return;
            }

            for (UMLRelation relation : parentDiagram.getRelationList()) {
                if (relation.getSource().equals(editedClass.getName())) {
                    relation.setSource(newName);
                }

                if (relation.getTarget().equals(editedClass.getName())) {
                    relation.setTarget(newName);
                }
            }

            editedClass.setName(newName);
            closeWindow(event);
        });

        // on Delete button click
        deleteButton.setOnAction(event -> {
            List<UMLRelation> relationsToRemove = new ArrayList<>();

            for (UMLRelation relation : parentDiagram.getRelationList()) {
                if (relation.getSource().equals(editedClass.getName()) ||
                    relation.getTarget().equals(editedClass.getName())) {
                    relationsToRemove.add(relation);
                }
            }

            for (UMLRelation relation : relationsToRemove) {
                parentDiagram.removeRelation(relation);
            }

            parentDiagram.removeClass(editedClass);
            closeWindow(event);
        });
    }

    /**
     * Set the edited UML class.
     *
     * @param editedClass edited UML class instance.
     * @param parentDiagram parent diagram instance.
     */
    public void setUMLClass (UMLClass editedClass, ClassDiagram parentDiagram) {
        this.editedClass = editedClass;
        this.parentDiagram = parentDiagram;
        nameTextField.setText(editedClass.getName());
    }

    /**
     * Close the window.
     *
     * @param e current event.
     */
    private void closeWindow (ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
