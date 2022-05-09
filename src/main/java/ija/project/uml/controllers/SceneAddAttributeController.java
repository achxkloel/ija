/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneAddAttributeController.java
 * Description: Controller for Add Attribute window.
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
 * Add attribute controller.
 */
public class SceneAddAttributeController extends EditWindowController {

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
     * Edited UML class.
     */
    UMLClass editedClass;

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

            if (editedClass.findAttribute(newName) != null) {
                displayAlertBlock("Attribute \"" + newName + "\" is already exist!");
                return;
            }

            editedClass.addAttribute(new Attribute(
                    newName,
                    newType,
                    newVisibility
            ));
            closeWindow(event);
        });
    }

    /**
     * Set edited UML class.
     *
     * @param editedClass UML class.
     */
    public void setUMLClass (UMLClass editedClass) {
        this.editedClass = editedClass;
    }
}
