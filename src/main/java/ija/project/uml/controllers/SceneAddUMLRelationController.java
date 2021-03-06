/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneAddUMLRelationController.java
 * Description: Controller for Add UML Relation window.
 */

package ija.project.uml.controllers;

import ija.project.uml.ClassDiagram;
import ija.project.uml.UMLClass;
import ija.project.uml.UMLRelation;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

/**
 * Add UML Relation window.
 */
public class SceneAddUMLRelationController extends EditWindowController {

    /**
     * Main block.
     */
    @FXML
    VBox mainBlock;

    /**
     * Add button.
     */
    @FXML
    Button addButton;

    /**
     * Name text field.
     */
    @FXML
    TextField nameTextField;

    /**
     * Type combo box.
     */
    @FXML
    ComboBox<String> typeComboBox;

    /**
     * Source combo box.
     */
    @FXML
    ComboBox<String> sourceComboBox;

    /**
     * Target combo box.
     */
    @FXML
    ComboBox<String> targetComboBox;

    /**
     * Parent class diagram.
     */
    ClassDiagram parentDiagram;

    /**
     * Main pane.
     */
    Pane mainPane;

    /**
     * Main controller.
     */
    SceneMainController sceneMainController;

    /**
     * Controller initialization.
     */
    @FXML
    public void initialize () {
        setParentBlock(mainBlock);

        addButton.setOnAction(event -> {
            String relationName = nameTextField.getText().trim();
            String type = typeComboBox.getValue();
            String source = sourceComboBox.getValue();
            String target = targetComboBox.getValue();

            if (relationName.isEmpty()) {
                displayAlertBlock("Name is empty!");
                return;
            }

            if (source.equals(target)) {
                displayAlertBlock("Cannot connect the same class!");
                return;
            }

            if (parentDiagram.findRelation(source, target) != null) {
                displayAlertBlock("Relation is already exist!");
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

    /**
     * Set the parent diagram.
     *
     * @param parentDiagram parent diagram instance.
     * @param mainPane main pane instance.
     * @param controller main controller instance.
     */
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
}
