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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.util.Scanner;

/**
 * Add method controller.
 */
public class SceneAddMethodController extends EditWindowController {

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
        setParentBlock(mainBlock);

        saveButton.setOnAction(event -> {
            String newName = methodNameTextField.getText().trim();
            String newReturnType = methodReturnTypeTextField.getText().trim();
            String newVisibility = methodVisibilityComboBox.getValue();
            String newParams = methodParamsTextArea.getText().trim();

            if (newName.isEmpty()) {
                displayAlertBlock("Name is empty!");
                return;
            }

            if (editedClass.findMethod(newName) != null) {
                displayAlertBlock("Method \"" + newName + "\" is already exist!");
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
                            displayAlertBlock("Wrong method parameters!");
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
}
