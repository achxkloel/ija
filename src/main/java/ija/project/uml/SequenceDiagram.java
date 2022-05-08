/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SequenceDiagram.java
 * Description: Sequence Diagram class
 */

package ija.project.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * Sequence diagram.
 */
public class SequenceDiagram extends Element {

    private final List<String> sequenceObjects = new ArrayList<>();
    private final List<Message> messageList = new ArrayList<>();

    public SequenceDiagram(String name) {
        super(name);
    }

    public void addSequenceObject(String sequenceObject) {
        this.sequenceObjects.add(sequenceObject);
    }

    public void addMessage(Message message) {
        this.messageList.add(message);
    }

    public List<String> getSequenceObjects() {
        return sequenceObjects;
    }

    public List<Message> getMessageList() {
        return messageList;
    }
}
