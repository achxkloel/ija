/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneMainController.java
 * Description: Controller for the main scene, which displays diagrams
 */

package ija.project.uml;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * Controller for the main scene, which displays diagrams.
 */
public class SceneMainController {

    @FXML
    Pane mainPane;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    ClassDiagram classDiagram;

    public void setClassDiagram (ClassDiagram classDiagram) {
        this.classDiagram = classDiagram;
    }

    private Label getLabel(String text) {
        Label label = new Label();
        label.setText(text);

        String cssLabel = "-fx-text-fill: black;\n" +
                "-fx-padding: 5px";

        label.setStyle(cssLabel);

        return label;
    }

    private VBox getAttributes(String text) {
        Text attributes = new Text();
        attributes.setText(text);

        final VBox vboxAttributes = new VBox();
        vboxAttributes.getChildren().add(attributes);

        String cssLayoutNestedVbox = "-fx-border-color: black;\n" +
                "-fx-border-width: 2;\n" +
                "-fx-border-style: solid none none none;\n" +
                "-fx-background-color: white;\n" +
                "-fx-padding: 3px";
        vboxAttributes.setStyle(cssLayoutNestedVbox);

        return vboxAttributes;
    }

    /**
     * method to display a string, that was passed to it
     * @param classDiagram the displayed diagram
     */
    public void displayResult(ClassDiagram classDiagram) {
        System.out.println(classDiagram);
        final VBox vbox = new VBox();
        String cssLayoutVbox = "-fx-border-color: black;\n" +
                "-fx-border-width: 2;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-background-color: white;\n";

        vbox.setStyle(cssLayoutVbox);
        vbox.getChildren().add(getLabel("Test label"));
        vbox.getChildren().add(getAttributes("Test attribute 1\nTest attribute 2"));
        vbox.getChildren().add(getAttributes("Test method 1\nTest method 2\nTest method 3"));

        vbox.setOnMousePressed(vboxOnMousePressedEventHandler);
        vbox.setOnMouseDragged(vboxOnMouseDraggedEventHandler);

        mainPane.getChildren().add(vbox);
    }

    EventHandler<MouseEvent> vboxOnMousePressedEventHandler =
        new EventHandler<>() {
            @Override
            public void handle(MouseEvent t) {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                orgTranslateX = ((VBox)(t.getSource())).getTranslateX();
                orgTranslateY = ((VBox)(t.getSource())).getTranslateY();
            }
        };

    EventHandler<MouseEvent> vboxOnMouseDraggedEventHandler =
        new EventHandler<>() {
            @Override
            public void handle(MouseEvent t) {
                double newTranslateX = orgTranslateX + t.getSceneX() - orgSceneX;
                double newTranslateY = orgTranslateY + t.getSceneY() - orgSceneY;

                double originalX = ((VBox)(t.getSource())).getLayoutX() + ((VBox)(t.getSource())).getWidth() / 2;
                double originalY = ((VBox)(t.getSource())).getLayoutY() + ((VBox)(t.getSource())).getHeight() / 2;

                double paneX = mainPane.getWidth();
                double paneY = mainPane.getHeight();

                if (originalX + newTranslateX < 0) newTranslateX = -originalX;
                if (originalX + newTranslateX > paneX) newTranslateX = paneX - originalX;
                if (originalY + newTranslateY < 0) newTranslateY = -originalY;
                if (originalY + newTranslateY > paneY) newTranslateY = paneY - originalY;

                ((VBox)(t.getSource())).setTranslateX(newTranslateX);
                ((VBox)(t.getSource())).setTranslateY(newTranslateY);
            }
        };

}
