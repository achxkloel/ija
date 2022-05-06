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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


/**
 * Controller for the main scene, which displays diagrams.
 */
public class SceneMainController {

    @FXML
    Pane mainPane;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    /**
     * method to display a string, that was passed to it
     * @param result the displayed string
     */
    public void displayResult(String result) {
        final VBox vbox = new VBox();
        final VBox nestedVbox = new VBox();

        Text testText = new Text();
        testText.setText("Test attribute");
        Text testText1 = new Text();
        testText1.setText("Test attribute 1");

        String cssLayout = "-fx-border-color: black;\n" +
                "-fx-border-width: 2;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-background-color: white;\n";

        vbox.setStyle(cssLayout);
        nestedVbox.setStyle(cssLayout);
        vbox.getChildren().add(testText);
        vbox.getChildren().add(nestedVbox);
        nestedVbox.getChildren().add(testText1);

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
