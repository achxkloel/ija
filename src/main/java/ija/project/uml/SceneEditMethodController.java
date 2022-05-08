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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Scanner;

public class SceneEditMethodController {

    @FXML
    Button saveButton;

    @FXML
    Button deleteButton;

    @FXML
    TextField methodVisibilityTextField;

    @FXML
    TextField methodNameTextField;

    @FXML
    TextField methodReturnTypeTextField;

    @FXML
    TextArea methodParamsTextArea;

    UMLClass parentClass;

    Method editedMethod;

    @FXML
    public void initialize () {
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (methodVisibilityTextField.getText().isEmpty() ||
                    methodNameTextField.getText().isEmpty() ||
                    methodReturnTypeTextField.getText().isEmpty() ||
                    methodParamsTextArea.getText().isEmpty()) {
                    System.out.println("Some text fields are empty");
                    return;
                }

                String newName = methodNameTextField.getText().trim();
                String newType = methodReturnTypeTextField.getText().trim();
                String newVisibility = methodVisibilityTextField.getText().trim();
                String newParams = methodParamsTextArea.getText().trim();

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
            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parentClass.removeMethod(editedMethod);
                closeWindow(event);
            }
        });
    }

    public void setMethod (Method editedMethod, UMLClass parentClass) {
        this.editedMethod = editedMethod;
        this.parentClass = parentClass;
        methodVisibilityTextField.setText(editedMethod.getVisibility());
        methodNameTextField.setText(editedMethod.getName());
        methodReturnTypeTextField.setText(editedMethod.getType());

        for (Attribute param : editedMethod.getAttributeList()) {
            methodParamsTextArea.appendText(param.getName() + ":" + param.getType() + "\n");
        }
    }

    public void closeWindow (ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}