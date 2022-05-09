/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        EditWindowController.java
 * Description: Base controller for edit windows.
 */

package ija.project.uml.controllers;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Edit Window Controller.
 */
public class EditWindowController {

    /**
     * Parent block.
     */
    private VBox parentBlock = null;

    /**
     * Block to show error messages.
     */
    private HBox alertBlock = null;

    /**
     * Alert error message.
     */
    private Text alertMessage = null;

    /**
     * Set the parent block.
     *
     * @param parentBlock parent block.
     */
    void setParentBlock(VBox parentBlock) {
        this.parentBlock = parentBlock;
    }

    /**
     * Display alert block.
     *
     * @param message message to display.
     */
    void displayAlertBlock (String message) {
        if (parentBlock == null) {
            return;
        }

        if (alertBlock == null) {
            this.createAlertBlock();

            // Add alert block to the start of parent VBox
            parentBlock.getChildren().add(0, alertBlock);
        }

        alertMessage.setText(message);
    }

    /**
     * Create alert block.
     */
    private void createAlertBlock () {
        alertBlock = new HBox();
        alertBlock.getStyleClass().add("alertBlock");
        alertBlock.setPadding(new Insets(5,5,5,5));
        alertBlock.setAlignment(Pos.CENTER_LEFT);

        alertMessage = new Text();
//        alertMessage.getStyleClass().add("alertMessage");
        alertMessage.setFill(Paint.valueOf("WHITE"));

        alertBlock.getChildren().add(alertMessage);
    }

    /**
     * Close the window.
     *
     * @param e current event.
     */
    void closeWindow(ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
