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
    Pane mainPane;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    double originalX, originalY;

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

        mainPane.getChildren().add(text);
        mainPane.getChildren().add(circle);
    }

    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
                    orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
                    originalX = t.getScreenX();
                    originalY = t.getScreenY();
                }
            };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double newTranslateX = orgTranslateX + t.getSceneX() - orgSceneX;
                    double newTranslateY = orgTranslateY + t.getSceneY() - orgSceneY;
                    double originalX = ((Circle)(t.getSource())).getCenterX();
                    double originalY = ((Circle)(t.getSource())).getCenterY();
                    double paneX = mainPane.getWidth();
                    double paneY = mainPane.getHeight();

                    if (originalX + newTranslateX < 0) newTranslateX = -originalX;
                    if (originalX + newTranslateX > paneX) newTranslateX = paneX - originalX;
                    if (originalY + newTranslateY < 0) newTranslateY = -originalY;
                    if (originalY + newTranslateY > paneY) newTranslateY = paneY - originalY;

                    ((Circle)(t.getSource())).setTranslateX(newTranslateX);
                    ((Circle)(t.getSource())).setTranslateY(newTranslateY);
                }
            };

}
