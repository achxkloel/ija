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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the sequence diagram scene, which displays diagrams.
 */
public class SceneSequenceController {

    @FXML
    private Pane mainPane;
    private Stage stage;
    private SequenceDiagram sequenceDiagram;
    private final List<VBox> sequenceVBoxList = new ArrayList<>();
    private double positionX = 50;
    private double positionY = 50;

    public void quitApplication() {
        Platform.exit();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        for (VBox vbox : sequenceVBoxList) {
            System.out.println(vbox.getWidth());
        }
    }

    public void setSequenceDiagram(SequenceDiagram sequenceDiagram) {
        this.sequenceDiagram = sequenceDiagram;
    }

    private void createSequenceVBox(String name) {
        VBox vbox = getSequenceVBox(name);
        vbox.setTranslateX(positionX);
        vbox.setTranslateY(positionY);
        positionX += 150;
        sequenceVBoxList.add(vbox);
    }

    public void displayDiagram() {
        for (String object : sequenceDiagram.getSequenceObjects())
            createSequenceVBox(object);

        for (VBox vbox : sequenceVBoxList)
            mainPane.getChildren().add(vbox);
    }

    public VBox getSequenceVBox(String name) {
        final VBox vbox = new VBox();

        String cssLayoutVbox = "-fx-border-color: black;\n" +
                "-fx-border-width: 2;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-background-color: white;\n";

        vbox.setStyle(cssLayoutVbox);
        vbox.getChildren().add(getLabel(name));

        vbox.toFront();

        return vbox;
    }

    private Label getLabel(String string) {
        Label label = new Label();
        label.setText(string);

        String cssLabel = "-fx-text-fill: black;\n" +
                "-fx-padding: 5px";

        label.setStyle(cssLabel);

        return label;
    }
}
