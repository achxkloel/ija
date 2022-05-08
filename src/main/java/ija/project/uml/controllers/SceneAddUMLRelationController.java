/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneMainController.java
 * Description: Controller for the welcome scene, which contains the file select button
 */

package ija.project.uml.controllers;

import ija.project.uml.ClassDiagram;
import ija.project.uml.UMLClass;
import ija.project.uml.UMLRelation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.List;

/**
 * Controller for the editUMLClass scene.
 */
public class SceneAddUMLRelationController {

    @FXML
    Button addButton;

    @FXML
    TextField nameTextField;

    @FXML
    ComboBox<String> typeComboBox;

    @FXML
    ComboBox<String> sourceComboBox;

    @FXML
    ComboBox<String> targetComboBox;

    ClassDiagram parentDiagram;

    Pane mainPane;

    SceneMainController sceneMainController;

    @FXML
    public void initialize () {
        addButton.setOnAction(event -> {
            String relationName = nameTextField.getText().trim();
            String type = typeComboBox.getValue();
            String source = sourceComboBox.getValue();
            String target = targetComboBox.getValue();

            if (relationName.isEmpty()) {
                System.out.println("Relation name is empty!");
                return;
            }

            if (source.equals(target)) {
                System.out.println("Cannot connect the same class");
                return;
            }

            if (parentDiagram.findRelation(source, target) != null) {
                System.out.println("Relation is already exists");
                return;
            }

            UMLRelation newRelation = new UMLRelation(
                    relationName,
                    type,
                    source,
                    target,
                    "",
                    ""
            );

            parentDiagram.addRelation(newRelation);

            newRelation.setVboxFrom(parentDiagram.findClass(source).getClassView());
            newRelation.setVboxTo(parentDiagram.findClass(target).getClassView());

            Line line = new Line();
            newRelation.setLine(line);
            newRelation.updateCoordinates();

            mainPane.getChildren().add(newRelation.getLine());

            if (newRelation.getArrow() != null)
                mainPane.getChildren().add(newRelation.getArrow());

            sceneMainController.moveVBoxesToFront();

            closeWindow(event);
        });
    }

    public void setParentDiagram (ClassDiagram parentDiagram, Pane mainPane, SceneMainController controller) {
        this.parentDiagram = parentDiagram;
        this.mainPane = mainPane;
        this.sceneMainController = controller;

        ObservableList<String> sourceList = sourceComboBox.getItems();
        ObservableList<String> targetList = targetComboBox.getItems();

        String selectedValue = null;

        for (UMLClass umlClass : parentDiagram.getClassList()) {
            if (selectedValue == null) {
                selectedValue = umlClass.getName();
            }

            sourceList.add(umlClass.getName());
            targetList.add(umlClass.getName());
        }

        if (selectedValue != null) {
            sourceComboBox.setValue(selectedValue);
            targetComboBox.setValue(selectedValue);
        }
    }

    public void closeWindow (ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
