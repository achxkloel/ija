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

    public void openFileWindow(ActionEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Class Diagram File");
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            switchToSceneMain(event, selectedFile.getName());
        }
    }

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
