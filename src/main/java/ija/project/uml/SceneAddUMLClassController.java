/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneMainController.java
 * Description: Controller for the welcome scene, which contains the file select button
 */

package ija.project.uml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the editUMLClass scene.
 */
public class SceneAddUMLClassController {

    @FXML
    Button addButton;

    @FXML
    TextField nameTextField;

    ClassDiagram parentDiagram;

    SceneMainController mainController;

    @FXML
    public void initialize () {
        addButton.setOnAction(event -> {
            String className = nameTextField.getText().trim();

            if (className.isEmpty()) {
                System.out.println("Text field is empty!");
                return;
            }

            if (parentDiagram.findClass(className) != null) {
                System.out.println("Class \"" + className + "\" is already exists!");
                return;
            }

            UMLClass newClass = new UMLClass(className);
            parentDiagram.addClass(newClass);
            VBox classVBox = mainController.createClassVBox(newClass);
            parentDiagram.getDiagramView().getChildren().add(classVBox);
            closeWindow(event);
        });
    }

    public void setMainController(SceneMainController mainController) {
        this.mainController = mainController;
    }

    public void setParentDiagram (ClassDiagram parentDiagram) {
        this.parentDiagram = parentDiagram;
    }

    public void closeWindow (ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
