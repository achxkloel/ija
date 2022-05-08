/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneAddUMLClassController.java
 * Description: Controller for Add UML Class window.
 */

package ija.project.uml.controllers;

import ija.project.uml.ClassDiagram;
import ija.project.uml.UMLClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Add UML class controller.
 */
public class SceneAddUMLClassController {

    /**
     * Add button.
     */
    @FXML
    Button addButton;

    /**
     * Name text field.
     */
    @FXML
    TextField nameTextField;

    /**
     * Parent class diagram.
     */
    ClassDiagram parentDiagram;

    /**
     * Main controller.
     */
    SceneMainController mainController;

    /**
     * Controller initialization.
     */
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

    /**
     * Set the main controller.
     *
     * @param mainController main controller instance.
     */
    public void setMainController(SceneMainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Set the parent diagram.
     *
     * @param parentDiagram parent diagram instance.
     */
    public void setParentDiagram (ClassDiagram parentDiagram) {
        this.parentDiagram = parentDiagram;
    }

    /**
     * Close the window.
     *
     * @param e current event.
     */
    public void closeWindow (ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
