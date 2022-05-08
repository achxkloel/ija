/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneAddMethodController.java
 * Description: Controller for Add Method window.
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
 * Add method controller.
 */
public class SceneAddMethodController {

    /**
     * Save button.
     */
    @FXML
    Button saveButton;

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
     * Edited UML class.
     */
    UMLClass editedClass;

    /**
     * Controller initialization.
     */
    @FXML
    public void initialize () {
        saveButton.setOnAction(event -> {
            String newName = methodNameTextField.getText().trim();
            String newReturnType = methodReturnTypeTextField.getText().trim();
            String newVisibility = methodVisibilityComboBox.getValue();
            String newParams = methodParamsTextArea.getText().trim();

            if (newName.isEmpty() || newReturnType.isEmpty()) {
                System.out.println("Some text fields are empty");
                return;
            }

            if (editedClass.findMethod(newName) != null) {
                System.out.println("Method \"" + newName + "\" is already exists!");
                return;
            }

            Method newMethod = new Method(
                newName,
                newReturnType,
                newVisibility
            );

            // Add method params
            if (!newParams.isEmpty()) {
                try (Scanner scanner = new Scanner(newParams)) {
                    while (scanner.hasNextLine()) {
                        String param = scanner.nextLine();
                        String[] paramArr = param.split(":");

                        if (paramArr.length != 2) {
                            System.out.println("Wrong method parameters!");
                            return;
                        }

                        newMethod.addAttribute(new Attribute(
                                paramArr[0].trim(),
                                paramArr[1].trim()
                        ));
                    }
                }
            }

            editedClass.addMethod(newMethod);
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

    /**
     * Close the window.
     *
     * @param e current event.
     */
    private void closeWindow (ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
