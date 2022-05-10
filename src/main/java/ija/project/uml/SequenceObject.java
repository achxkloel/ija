/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SequenceObject.java
 * Description: Sequence object class
 */

package ija.project.uml;


import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.awt.*;

/**
 * Object of the sequence diagram.
 */
public class SequenceObject extends Element {

    /**
     * Object VBox
     */
    VBox objectVBox = null;

    /**
     * Object Label
     */
    Label objectLabel = null;

    /**
     * Object line
     */
    Line objectLine = null;

    /**
     * Creates new class.
     *
     * @param name name of class.
     */
    public SequenceObject (String name) {
        super(name);
    }

    /**
     * Override set name method.
     *
     * @param newName new name.
     */
    @Override
    public void setName(String newName) {
        super.setName(newName);

        if (this.objectLabel != null) {
            this.objectLabel.setText(newName);
        }
    }

    /**
     * Set object label.
     *
     * @param objectLabel object label.
     */
    public void setObjectLabel(Label objectLabel) {
        this.objectLabel = objectLabel;
    }

    /**
     * Set object vbox.
     *
     * @param objectVBox object vbox.
     */
    public void setObjectVBox(VBox objectVBox) {
        this.objectVBox = objectVBox;
    }

    /**
     * Set object line
     *
     * @param objectLine object line
     */
    public void setObjectLine(Line objectLine) {
        this.objectLine = objectLine;
    }

    /**
     * Set object to defined state.
     *
     * @param defined defined flag.
     */
    public void setDefined (boolean defined) {
        ObservableList<String> currVBoxClass = this.objectVBox.getStyleClass();
        ObservableList<String> currLabelClass = this.objectLabel.getStyleClass();
        currVBoxClass.clear();
        currLabelClass.clear();

        if (defined) {
            currVBoxClass.add("objectVBoxDefined");
            currLabelClass.add("objectLabelDefined");
            this.objectLine.setStroke(Color.BLACK);
        } else {
            currVBoxClass.add("objectVBoxNotDefined");
            currLabelClass.add("objectLabelNotDefined");
            this.objectLine.setStroke(Color.RED);
        }
    }
}