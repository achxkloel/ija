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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SceneAddAttributeController {

    @FXML
    Button saveButton;

    @FXML
    ComboBox<String> attributeVisibilityTextField;

    @FXML
    TextField attributeNameTextField;

    @FXML
    TextField attributeTypeTextField;

    UMLClass editedClass;

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
                String newVisibility = attributeVisibilityTextField.getValue();

                if (editedClass.findAttribute(newName) != null) {
                    System.out.println("Attribute \"" + newName + "\" is already exists!");
                    return;
                }

                editedClass.addAttribute(new Attribute(
                        newName,
                        newType,
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
