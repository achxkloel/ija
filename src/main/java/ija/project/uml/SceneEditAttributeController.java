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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SceneEditAttributeController {

    @FXML
    Button saveButton;

    @FXML
    Button deleteButton;

    @FXML
    ComboBox<String> attributeVisibilityComboBox;

    @FXML
    TextField attributeNameTextField;

    @FXML
    TextField attributeTypeTextField;

    UMLClass parentClass;

    Attribute editedAttribute;

    @FXML
    public void initialize () {
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (attributeNameTextField.getText().isEmpty() ||
                    attributeTypeTextField.getText().isEmpty()) {
                    System.out.println("Some text fields are empty");
                    return;
                }

                String newName = attributeNameTextField.getText().trim();
                String newType = attributeTypeTextField.getText().trim();
                String newVisibility = attributeVisibilityComboBox.getValue();

                if (!newName.equals(editedAttribute.getName()) &&
                    parentClass.findAttribute(newName) != null) {
                    System.out.println("Attribute \"" + newName + "\" is already exists!");
                    return;
                }

                editedAttribute.setName(newName);
                editedAttribute.setType(newType);
                editedAttribute.setVisibility(newVisibility);
                editedAttribute.updateTextView();
                closeWindow(event);
            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parentClass.removeAttribute(editedAttribute);
                closeWindow(event);
            }
        });
    }

    public void setAttribute (Attribute editedAttribute, UMLClass parentClass) {
        this.editedAttribute = editedAttribute;
        this.parentClass = parentClass;
        attributeVisibilityComboBox.setValue(editedAttribute.getVisibility());
        attributeNameTextField.setText(editedAttribute.getName());
        attributeTypeTextField.setText(editedAttribute.getType());
    }

    public void closeWindow (ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
