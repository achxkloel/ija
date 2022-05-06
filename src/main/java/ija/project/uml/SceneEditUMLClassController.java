/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneMainController.java
 * Description: Controller for the welcome scene, which contains the file select button
 */

package ija.project.uml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Controller for the editUMLClass scene.
 */
public class SceneEditUMLClassController {

    @FXML
    Button saveButton;

    @FXML
    TextField nameTextField;

    @FXML
    Label nameLabel;

    UMLClass editedClass;
    VBox classVBox;

    @FXML
    public void initialize () {
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!nameTextField.getText().isEmpty()) {
                    editedClass.setName(nameTextField.getText());
                    Label classVBoxLabel = (Label)(classVBox.getChildren().get(0));
                    classVBoxLabel.setText(nameTextField.getText());
                }
            }
        });
    }

    public void setUMLClass (UMLClass editedClass) {
        this.editedClass = editedClass;
        nameTextField.setText(editedClass.getName());
    }

    public void setVBox (VBox classVBox) {
        this.classVBox = classVBox;
    }
}
