/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneMainController.java
 * Description: Controller for the welcome scene, which the file select button
 */

package ija.project.uml;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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
        if (selectedFile != null) {
            ClassDiagram classDiagram = DataParser.parseClassDiagram(selectedFile);
            UMLClass CDClass = classDiagram.findClass("testClass");
            String CDName = "undefined";
            String CDAttribute = "undefined";
            String CDMethod = "undefined";

            if (CDClass != null) {
                CDName = CDClass.getName();
                Attribute attr = CDClass.findAttribute("testAttribute");
                if (attr != null) {
                    CDAttribute = attr.toString();
                }
                Method method = CDClass.findMethod("testMethod");
                if (method != null) {
                    CDMethod = method.toString();
                }
            }

            String output = "testClassDiagram\n" +
                            "Class:\n" +
                            "\tname: " + CDName + "\n" +
                            "Attribute:\n\t" + CDAttribute + "\n" +
                            "Method:\n\t" + CDMethod;

//            switchToSceneMain(event, selectedFile.getName());
            switchToSceneMain(event, output);
        }
    }

    /**
     * A method, that switches to the main scene
     * @param event is used for stage
     * @throws Exception
     */
    public void switchToSceneMain(ActionEvent event, String fileName) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/sceneMain.fxml"));
        root = loader.load();

        SceneMainController sceneMainController = loader.getController();
        sceneMainController.displayResult(fileName);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
