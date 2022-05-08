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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Scanner;

public class SceneAddMethodController {

    @FXML
    Button saveButton;

    @FXML
    ComboBox<String> methodVisibilityTextField;

    @FXML
    TextField methodNameTextField;

    @FXML
    TextField methodReturnTypeTextField;

    @FXML
    TextArea methodParamsTextArea;

    UMLClass editedClass;

    @FXML
    public void initialize () {
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (methodNameTextField.getText().isEmpty() ||
                    methodReturnTypeTextField.getText().isEmpty() ||
                    methodParamsTextArea.getText().isEmpty()) {
                    System.out.println("Some text fields are empty");
                    return;
                }

                String newName = methodNameTextField.getText().trim();
                String newReturnType = methodReturnTypeTextField.getText().trim();
                String newVisibility = methodVisibilityTextField.getValue();
                String newParams = methodParamsTextArea.getText().trim();

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
            }
        });
    }

    public void setUMLClass (UMLClass editedClass) {
        this.editedClass = editedClass;
    }

    public void closeWindow (ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
