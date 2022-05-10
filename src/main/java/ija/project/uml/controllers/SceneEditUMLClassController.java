/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneEditUMLClassController.java
 * Description: Controller for Edit UML Class window.
 */

package ija.project.uml.controllers;

import ija.project.uml.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;

/**
 * Edit UML Class controller.
 */
public class SceneEditUMLClassController extends EditWindowController {

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
     * Delete button.
     */
    @FXML
    Button deleteButton;

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
     * Edited UML Class.
     */
    UMLClass editedClass;

    /**
     * Controller initialization.
     */
    @FXML
    public void initialize () {
        setParentBlock(mainBlock);

        // on Save button click
        saveButton.setOnAction(event -> {
            String newName = nameTextField.getText().trim();

            if (newName.isEmpty()) {
                displayAlertBlock("Name is empty!");
                return;
            }

            if (!newName.equals(editedClass.getName()) &&
                parentDiagram.findClass(newName) != null) {
                displayAlertBlock("Class \"" + newName + "\" is already exist!");
                return;
            }

            for (UMLRelation relation : parentDiagram.getRelationList()) {
                if (relation.getSource().equals(editedClass.getName())) {
                    relation.setSource(newName);
                }

                if (relation.getTarget().equals(editedClass.getName())) {
                    relation.setTarget(newName);
                }
            }

            SequenceObject oldSeqObj = sequenceDiagram.findSequenceObject(editedClass.getName());
            SequenceObject newSeqObj = sequenceDiagram.findSequenceObject(newName);

            if (oldSeqObj != null) {
                oldSeqObj.setName(newName);
            }

            if (newSeqObj != null) {
                newSeqObj.setDefined(true);
            }

            editedClass.setName(newName);
            closeWindow(event);
        });

        // on Delete button click
        deleteButton.setOnAction(event -> {
            List<UMLRelation> relationsToRemove = new ArrayList<>();

            for (UMLRelation relation : parentDiagram.getRelationList()) {
                if (relation.getSource().equals(editedClass.getName()) ||
                    relation.getTarget().equals(editedClass.getName())) {
                    relationsToRemove.add(relation);
                }
            }

            for (UMLRelation relation : relationsToRemove) {
                parentDiagram.removeRelation(relation);
            }

            SequenceObject seqObj = sequenceDiagram.findSequenceObject(editedClass.getName());

            if (seqObj != null) {
                seqObj.setDefined(false);
            }

            parentDiagram.removeClass(editedClass);
            closeWindow(event);
        });
    }

    /**
     * Set the edited UML class.
     *
     * @param editedClass edited UML class instance.
     * @param parentDiagram parent diagram instance.
*    * @param sequenceDiagram sequence diagram.
     */
    public void setUMLClass (UMLClass editedClass, ClassDiagram parentDiagram, SequenceDiagram sequenceDiagram) {
        this.editedClass = editedClass;
        this.parentDiagram = parentDiagram;
        this.sequenceDiagram = sequenceDiagram;
        nameTextField.setText(editedClass.getName());
    }
}
