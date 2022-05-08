/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneDiagramController.java
 * Description: Controller for the main scene, which displays diagrams
 */

package ija.project.uml.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;


/**
 * Controller for the sequence diagram scene, which displays diagrams.
 */
public class SceneSequenceController {

    @FXML
    private Pane mainPane;
    private Stage stage;

    public void closeDiagram() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/sceneWelcome.fxml"));
        Parent root;

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        stage.show();
    }

    public void quitApplication() {
        Platform.exit();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
