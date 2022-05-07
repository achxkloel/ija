/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneMainController.java
 * Description: Controller for the welcome scene, which contains the file select button
 */

package ija.project.uml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the editUMLClass scene.
 */
public class SceneEditUMLClassController {

    @FXML
    Button saveButton;

    @FXML
    Button deleteButton;

    @FXML
    TextField nameTextField;

    ClassDiagram parentDiagram;

    UMLClass editedClass;

    @FXML
    public void initialize () {
        saveButton.setOnAction(event -> {
            if (nameTextField.getText().isEmpty()) {
                System.out.println("Text field is empty!");
                return;
            }

            String newName = nameTextField.getText().trim();

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

    public void setUMLClass (UMLClass editedClass, ClassDiagram parentDiagram) {
        this.editedClass = editedClass;
        this.parentDiagram = parentDiagram;
        nameTextField.setText(editedClass.getName());
    }

    public void closeWindow (ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
