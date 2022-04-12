package ija.project.uml;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SceneMainController {

    @FXML
    Label nameLabel;

    public void displayResult(String result) {
        nameLabel.setText(result);
    }

}
