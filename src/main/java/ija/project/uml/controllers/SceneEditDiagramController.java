/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneEditDiagramController.java
 * Description: Controller for Edit Diagram window.
 */

package ija.project.uml.controllers;

import ija.project.uml.ClassDiagram;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Edit Diagram controller.
 */
public class SceneEditDiagramController extends EditWindowController {

    /**
     * Main block.
     */
    @FXML
    VBox mainBlock;

    /**
     * Save button.
     */
    @FXML
    Button saveButton;

    /**
     * Name text field.
     */
    @FXML
    TextField nameTextField;

    /**
     * Parent stage.
     */
    Stage parentStage;

    /**
     * Edited class diagram.
     */
    ClassDiagram classDiagram;

    /**
     * Controller initialization.
     */
    @FXML
    public void initialize () {
        setParentBlock(mainBlock);

        saveButton.setOnAction(event -> {
            String newName = nameTextField.getText().trim();

            if (newName.isEmpty()) {
                displayAlertBlock("Name is empty!");
                return;
            }

            classDiagram.setName(newName);
            parentStage.setTitle(newName);
            closeWindow(event);
        });
    }

    /**
     * Set the edited class diagram.
     *
     * @param classDiagram class diagram.
     * @param parentStage parent stage instance.
     */
    public void setDiagram (ClassDiagram classDiagram, Stage parentStage) {
        this.classDiagram = classDiagram;
        this.parentStage = parentStage;
        nameTextField.setText(parentStage.getTitle());
    }
}
