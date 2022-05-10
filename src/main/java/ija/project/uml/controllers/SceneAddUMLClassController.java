/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneAddUMLClassController.java
 * Description: Controller for Add UML Class window.
 */

package ija.project.uml.controllers;

import ija.project.uml.ClassDiagram;
import ija.project.uml.SequenceDiagram;
import ija.project.uml.SequenceObject;
import ija.project.uml.UMLClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Add UML class controller.
 */
public class SceneAddUMLClassController extends EditWindowController {

    /**
     * Main block.
     */
    @FXML
    VBox mainBlock;

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
     * Sequence diagram.
     */
    SequenceDiagram sequenceDiagram;

    /**
     * Main controller.
     */
    SceneMainController mainController;

    /**
     * Controller initialization.
     */
    @FXML
    public void initialize () {
        setParentBlock(mainBlock);

        addButton.setOnAction(event -> {
            String className = nameTextField.getText().trim();

            if (className.isEmpty()) {
                displayAlertBlock("Name is empty!");
                return;
            }

            if (parentDiagram.findClass(className) != null) {
                displayAlertBlock("Class \"" + className + "\" is already exist!");
                return;
            }

            UMLClass newClass = new UMLClass(className);
            parentDiagram.addClass(newClass);
            VBox classVBox = mainController.createClassVBox(newClass);
            parentDiagram.getDiagramView().getChildren().add(classVBox);

            SequenceObject seqObj = sequenceDiagram.findSequenceObject(className);

            if (seqObj != null) {
                seqObj.setDefined(true);
            }

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
    public void setParentDiagram (ClassDiagram parentDiagram, SequenceDiagram sequenceDiagram) {
        this.parentDiagram = parentDiagram;
        this.sequenceDiagram = sequenceDiagram;
    }
}
