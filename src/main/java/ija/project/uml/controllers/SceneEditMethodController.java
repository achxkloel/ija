/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneEditMethodController.java
 * Description: Controller for Edit Method window.
 */

package ija.project.uml.controllers;

import ija.project.uml.Attribute;
import ija.project.uml.Method;
import ija.project.uml.UMLClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Scanner;

/**
 * Edit Method controller.
 */
public class SceneEditMethodController {

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
    ComboBox<String> methodVisibilityComboBox;

    /**
     * Name text field.
     */
    @FXML
    TextField methodNameTextField;

    /**
     * Return type text field.
     */
    @FXML
    TextField methodReturnTypeTextField;

    /**
     * Params text area.
     */
    @FXML
    TextArea methodParamsTextArea;

    /**
     * Parent UML class.
     */
    UMLClass parentClass;

    /**
     * Edited method.
     */
    Method editedMethod;

    /**
     * Controller initialization.
     */
    @FXML
    public void initialize () {
        // On add button click
        saveButton.setOnAction(event -> {
            String newName = methodNameTextField.getText().trim();
            String newType = methodReturnTypeTextField.getText().trim();
            String newVisibility = methodVisibilityComboBox.getValue();
            String newParams = methodParamsTextArea.getText().trim();

            if (newName.isEmpty() || newType.isEmpty()) {
                System.out.println("Some text fields are empty");
                return;
            }

            if (!newName.equals(editedMethod.getName()) &&
                parentClass.findAttribute(newName) != null) {
                System.out.println("Method \"" + newName + "\" is already exists!");
                return;
            }

            // Add method params
            editedMethod.clearAttributes();
            if (!newParams.isEmpty()) {
                try (Scanner scanner = new Scanner(newParams)) {
                    while (scanner.hasNextLine()) {
                        String param = scanner.nextLine();
                        String[] paramArr = param.split(":");

                        if (paramArr.length != 2) {
                            System.out.println("Wrong method parameters!");
                            return;
                        }

                        editedMethod.addAttribute(new Attribute(
                                paramArr[0].trim(),
                                paramArr[1].trim()
                        ));
                    }
                }
            }

            editedMethod.setName(newName);
            editedMethod.setType(newType);
            editedMethod.setVisibility(newVisibility);
            editedMethod.updateTextView();
            closeWindow(event);
        });

        // On delete button click
        deleteButton.setOnAction(event -> {
            parentClass.removeMethod(editedMethod);
            closeWindow(event);
        });
    }

    /**
     * Set the edited method.
     *
     * @param editedMethod edited method instance.
     * @param parentClass parent UML class instance.
     */
    public void setMethod (Method editedMethod, UMLClass parentClass) {
        this.editedMethod = editedMethod;
        this.parentClass = parentClass;
        methodVisibilityComboBox.setValue(editedMethod.getVisibility());
        methodNameTextField.setText(editedMethod.getName());
        methodReturnTypeTextField.setText(editedMethod.getType());

        for (Attribute param : editedMethod.getAttributeList()) {
            methodParamsTextArea.appendText(param.getName() + ":" + param.getType() + "\n");
        }
    }

    /**
     * Close thw window.
     *
     * @param e current event.
     */
    private void closeWindow (ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
