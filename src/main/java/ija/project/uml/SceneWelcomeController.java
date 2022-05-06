/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneWelcomeController.java
 * Description: Controller for the welcome scene, which contains the file select button
 */

package ija.project.uml;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Controller for the welcome scene, which the file select button.
 */
public class SceneWelcomeController {

    private Stage stage;
    private Parent root;

    /**
     * A method, that opens the file chooser, after the button on the welcome scene has been pressed
     * @param event is used later for stage
     * @throws Exception
     */
    public void openFileWindow(ActionEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Class Diagram File");
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile == null) {
            return;
        }

        ClassDiagram classDiagram = DataParser.parseClassDiagram(selectedFile);

        if (classDiagram == null) {
            return;
        }

        switchToSceneMain(event, classDiagram);
    }

    /**
     * A method, that switches to the main scene
     * @param event is used for stage
     * @throws Exception
     */
    public void switchToSceneMain(ActionEvent event, ClassDiagram classDiagram) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/sceneMain.fxml"));
        root = loader.load();

        SceneMainController sceneMainController = loader.getController();
        sceneMainController.setClassDiagram(classDiagram);
        sceneMainController.displayVbox();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setMinWidth(1000);
        stage.setMinHeight(720);
        stage.show();
    }

    /**
     * A method, that shuts down the application
     */
    public void quitApplication() {
        Platform.exit();
    }
}
