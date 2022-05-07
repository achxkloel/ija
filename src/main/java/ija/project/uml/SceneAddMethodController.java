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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SceneAddMethodController {

    @FXML
    Button saveButton;

    @FXML
    TextField methodVisibilityTextField;

    @FXML
    TextField methodNameTextField;

    @FXML
    TextField methodReturnTypeTextField;

    UMLClass editedClass;

    @FXML
    public void initialize () {
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (methodVisibilityTextField.getText().isEmpty() ||
                    methodNameTextField.getText().isEmpty() ||
                    methodReturnTypeTextField.getText().isEmpty()) {
                    System.out.println("Some text fields are empty");
                    return;
                }

                String newName = methodNameTextField.getText().trim();
                String newReturnType = methodReturnTypeTextField.getText().trim();
                String newVisibility = methodVisibilityTextField.getText().trim();

                if (editedClass.findMethod(newName) != null) {
                    System.out.println("Method \"" + newName + "\" is already exists!");
                    return;
                }

                editedClass.addMethod(new Method(
                        newName,
                        newReturnType,
                        newVisibility
                ));
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
