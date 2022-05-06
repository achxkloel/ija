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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


/**
 * Controller for the main scene, which displays diagrams.
 */
public class SceneMainController {

    @FXML
    Pane mainCanvas;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    /**
     * method to display a string, that was passed to it
     * @param result the displayed string
     */
    public void displayResult(String result) {
        Text text = new Text();
        text.setText(result);

        Circle circle = new Circle();
        circle.setCenterX(50.0f);
        circle.setCenterY(50.0f);
        circle.setRadius(50.0f);
        circle.setOnMousePressed(circleOnMousePressedEventHandler);
        circle.setOnMouseDragged(circleOnMouseDraggedEventHandler);

        mainCanvas.getChildren().add(text);
        mainCanvas.getChildren().add(circle);
    }

    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
                    orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
                }
            };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((Circle)(t.getSource())).setTranslateX(newTranslateX);
                    ((Circle)(t.getSource())).setTranslateY(newTranslateY);
                }
            };

}
