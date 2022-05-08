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
public class SceneEditDiagramController {

    @FXML
    Button saveButton;

    @FXML
    TextField nameTextField;

    Stage parentStage;

    ClassDiagram classDiagram;

    @FXML
    public void initialize () {
        saveButton.setOnAction(event -> {
            String newName = nameTextField.getText().trim();

            if (newName.isEmpty()) {
                System.out.println("Text field is empty!");
                return;
            }

            classDiagram.setName(newName);
            parentStage.setTitle(newName);
            closeWindow(event);
        });
    }

    public void setDiagram (ClassDiagram classDiagram, Stage parentStage) {
        this.classDiagram = classDiagram;
        this.parentStage = parentStage;
        nameTextField.setText(parentStage.getTitle());
    }

    public void closeWindow (ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
