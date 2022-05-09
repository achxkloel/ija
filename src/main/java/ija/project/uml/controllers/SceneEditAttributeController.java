/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneEditAttributeController.java
 * Description: Controller for Edit Attribute window.
 */

package ija.project.uml.controllers;

import ija.project.uml.Attribute;
import ija.project.uml.UMLClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Edit Attribute controller.
 */
public class SceneEditAttributeController extends EditWindowController {

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
     * Delete button.
     */
    @FXML
    Button deleteButton;

    /**
     * Visibility combo box.
     */
    @FXML
    ComboBox<String> attributeVisibilityComboBox;

    /**
     * Name text field.
     */
    @FXML
    TextField attributeNameTextField;

    /**
     * Type text field.
     */
    @FXML
    TextField attributeTypeTextField;

    /**
     * Parent UML class.
     */
    UMLClass parentClass;

    /**
     * Edited attribute.
     */
    Attribute editedAttribute;

    /**
     * Controller initialization.
     */
    @FXML
    public void initialize () {
        setParentBlock(mainBlock);

        // On save button click
        saveButton.setOnAction(event -> {
            String newName = attributeNameTextField.getText().trim();
            String newType = attributeTypeTextField.getText().trim();
            String newVisibility = attributeVisibilityComboBox.getValue();

            if (newName.isEmpty() || newType.isEmpty()) {
                displayAlertBlock("Some text fields are empty!");
                return;
            }

            if (!newName.equals(editedAttribute.getName()) &&
                parentClass.findAttribute(newName) != null) {
                displayAlertBlock("Attribute \"" + newName + "\" is already exist!");
                return;
            }

            editedAttribute.setName(newName);
            editedAttribute.setType(newType);
            editedAttribute.setVisibility(newVisibility);
            editedAttribute.updateTextView();
            closeWindow(event);
        });

        // On delete button click.
        deleteButton.setOnAction(event -> {
            parentClass.removeAttribute(editedAttribute);
            closeWindow(event);
        });
    }

    /**
     * Set the edited attribute.
     *
     * @param editedAttribute attribute instance.
     * @param parentClass parent UML class instance.
     */
    public void setAttribute (Attribute editedAttribute, UMLClass parentClass) {
        this.editedAttribute = editedAttribute;
        this.parentClass = parentClass;
        attributeVisibilityComboBox.setValue(editedAttribute.getVisibility());
        attributeNameTextField.setText(editedAttribute.getName());
        attributeTypeTextField.setText(editedAttribute.getType());
    }
}
