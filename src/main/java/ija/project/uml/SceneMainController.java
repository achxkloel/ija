/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneMainController.java
 * Description: Controller for the main scene, which displays diagrams
 */

package ija.project.uml;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


/**
 * Controller for the main scene, which displays diagrams.
 */
public class SceneMainController {

    @FXML
    Label nameLabel;

    /**
     * method to display a string, that was passed to it
     * @param result the displayed string
     */
    public void displayResult(String result) {
        nameLabel.setText(result);
    }

}
