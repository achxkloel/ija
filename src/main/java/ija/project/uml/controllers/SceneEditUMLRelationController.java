/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneMainController.java
 * Description: Controller for the welcome scene, which contains the file select button
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

public class SceneEditUMLRelationController {

    @FXML
    Button saveButton;

    @FXML
    Button directionButton;

    @FXML
    Button deleteButton;

    @FXML
    ComboBox<String> typeComboBox;

    @FXML
    TextField nameTextField;

    ClassDiagram parentDiagram;

    UMLRelation editedRelation;

    @FXML
    public void initialize () {
        saveButton.setOnAction(event -> {
            String newName = nameTextField.getText().trim();
            String newType = typeComboBox.getValue();

            if (newName.isEmpty()) {
                System.out.println("Some text fields are empty");
                return;
            }

            editedRelation.setName(newName);
            editedRelation.setType(newType);
            editedRelation.updateCoordinates();

            closeWindow(event);
        });

        directionButton.setOnAction(event -> {
            // Change source and target
            String tmpClassName = editedRelation.getSource();
            editedRelation.setSource(editedRelation.getTarget());
            editedRelation.setTarget(tmpClassName);

            // Change vbox from and vbox to
            VBox tmpClassVBox = editedRelation.getVboxFrom();
            editedRelation.setVboxFrom(editedRelation.getVboxTo());
            editedRelation.setVboxTo(tmpClassVBox);

            editedRelation.updateCoordinates();
            closeWindow(event);
        });

        deleteButton.setOnAction(event -> {
            parentDiagram.removeRelation(editedRelation);
            closeWindow(event);
        });
    }

    public void setRelation (UMLRelation editedRelation, ClassDiagram parentDiagram) {
        this.editedRelation = editedRelation;
        this.parentDiagram = parentDiagram;

        nameTextField.setText(editedRelation.getName());
        typeComboBox.setValue(editedRelation.getType());
    }

    public void closeWindow (ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
