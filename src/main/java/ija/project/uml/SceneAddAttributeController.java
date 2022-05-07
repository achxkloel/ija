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

public class SceneAddAttributeController {

    @FXML
    Button saveButton;

    @FXML
    TextField attributeVisibilityTextField;

    @FXML
    TextField attributeNameTextField;

    @FXML
    TextField attributeTypeTextField;

    UMLClass editedClass;

    @FXML
    public void initialize () {
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!attributeVisibilityTextField.getText().isEmpty() &&
                    !attributeNameTextField.getText().isEmpty() &&
                    !attributeTypeTextField.getText().isEmpty()) {
                    Attribute newAttribute = new Attribute(
                        attributeNameTextField.getText(),
                        attributeTypeTextField.getText(),
                        attributeVisibilityTextField.getText()
                    );
                    editedClass.addAttribute(newAttribute);
                }
            }
        });
    }

    public void setUMLClass (UMLClass editedClass) {
        this.editedClass = editedClass;
    }
}
