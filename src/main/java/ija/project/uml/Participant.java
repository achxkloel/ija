/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        Participant.java
 * Description: Participant class
 */

package ija.project.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * Participant of the sequence diagram.
 */
public class Participant extends Element {

    private final List<Action> actionList = new ArrayList<>();

    public Participant (String name) {
        super(name);
    }

    public void addAction (Action newAction) {
        actionList.add(newAction);
    }

    public List<Action> getActionList () {
        return actionList;
    }
}
