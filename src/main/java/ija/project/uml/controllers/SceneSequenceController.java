/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneDiagramController.java
 * Description: Controller for the main scene, which displays diagrams
 */

package ija.project.uml.controllers;

import ija.project.uml.SequenceDiagram;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Controller for the sequence diagram scene, which displays diagrams.
 */
public class SceneSequenceController {

    @FXML
    private Pane mainPane;
    private Stage stage;
    private SequenceDiagram sequenceDiagram;

    public void quitApplication() {
        Platform.exit();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSequenceDiagram(SequenceDiagram sequenceDiagram) {
        this.sequenceDiagram = sequenceDiagram;
    }
}
