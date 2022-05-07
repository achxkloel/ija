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
import javafx.scene.control.TextField;

public class SceneEditAttributeController {

    @FXML
    Button saveButton;

    @FXML
    TextField attributeVisibilityTextField;

    @FXML
    TextField attributeNameTextField;

    @FXML
    TextField attributeTypeTextField;

    Attribute editedAttribute;

    @FXML
    public void initialize () {
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!attributeVisibilityTextField.getText().isEmpty() &&
                    !attributeNameTextField.getText().isEmpty() &&
                    !attributeTypeTextField.getText().isEmpty()) {
                    editedAttribute.setName(attributeNameTextField.getText());
                    editedAttribute.setType(attributeTypeTextField.getText());
                    editedAttribute.setVisibility(attributeVisibilityTextField.getText());
                    editedAttribute.updateAttributeText();
                }
            }
        });
    }

    public void setAttribute (Attribute editedAttribute) {
        this.editedAttribute = editedAttribute;
        attributeVisibilityTextField.setText(editedAttribute.getVisibility());
        attributeNameTextField.setText(editedAttribute.getName());
        attributeTypeTextField.setText(editedAttribute.getType());
    }
}
