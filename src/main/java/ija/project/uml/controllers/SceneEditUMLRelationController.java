/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneEditUMLRelationController.java
 * Description: Controller for Edit UML Relation window.
 */

package ija.project.uml.controllers;

import ija.project.uml.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Edit UML Relation controller.
 */
public class SceneEditUMLRelationController {

    /**
     * Save button.
     */
    @FXML
    Button saveButton;

    /**
     * Direction button.
     */
    @FXML
    Button directionButton;

    /**
     * Delete button.
     */
    @FXML
    Button deleteButton;

    /**
     * Type combo box.
     */
    @FXML
    ComboBox<String> typeComboBox;

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
     * Edited UML relation.
     */
    UMLRelation editedRelation;

    /**
     * Main controller.
     */
    SceneMainController sceneMainController;

    @FXML
    public void initialize () {
        // on Save button click
        saveButton.setOnAction(event -> {
            String newName = nameTextField.getText().trim();
            String newType = typeComboBox.getValue();

            if (newName.isEmpty()) {
                System.out.println("Some text fields are empty");
                return;
            }

            editedRelation.setName(newName);
            editedRelation.setType(newType);
            sceneMainController.clearPolygon(editedRelation);
            editedRelation.updateCoordinates();
            sceneMainController.addPolygon(editedRelation);

            closeWindow(event);
        });

        // on Direction button click
        directionButton.setOnAction(event -> {
            // Change source and target
            String tmpClassName = editedRelation.getSource();
            editedRelation.setSource(editedRelation.getTarget());
            editedRelation.setTarget(tmpClassName);

            // Change vbox from and vbox to
            VBox tmpClassVBox = editedRelation.getVboxFrom();
            editedRelation.setVboxFrom(editedRelation.getVboxTo());
            editedRelation.setVboxTo(tmpClassVBox);

            sceneMainController.clearPolygon(editedRelation);
            editedRelation.updateCoordinates();
            sceneMainController.addPolygon(editedRelation);
            closeWindow(event);
        });

        // on Delete button click
        deleteButton.setOnAction(event -> {
            parentDiagram.removeRelation(editedRelation);
            closeWindow(event);
        });
    }

    /**
     * Set the edited UML relation.
     *
     * @param editedRelation UML relation instance.
     * @param parentDiagram parent diagram instance.
     * @param controller main controller instance.
     */
    public void setRelation (UMLRelation editedRelation, ClassDiagram parentDiagram, SceneMainController controller) {
        this.editedRelation = editedRelation;
        this.parentDiagram = parentDiagram;
        this.sceneMainController = controller;

        nameTextField.setText(editedRelation.getName());
        typeComboBox.setValue(editedRelation.getType());
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
