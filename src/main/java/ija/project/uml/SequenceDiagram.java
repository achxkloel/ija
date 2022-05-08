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

    /**
     * Constructor for the sequence diagram
     * @param name name of the sequence diagram
     */
    public SequenceDiagram(String name) {
        super(name);
    }

    /**
     * Adds new sequence object to the list of sequence objects
     * @param sequenceObject new sequence object
     */
    public void addSequenceObject(String sequenceObject) {
        this.sequenceObjects.add(sequenceObject);
    }

    /**
     * Adds new message to the list of messages
     * @param message new message
     */
    public void addMessage(Message message) {
        this.messageList.add(message);
    }

    /**
     * Getter for the list of sequence objects.
     * @return list of sequence objects
     */
    public List<String> getSequenceObjects() {
        return sequenceObjects;
    }

    /**
     * Getter for the list of messages
     * @return list of messages
     */
    public List<Message> getMessageList() {
        return messageList;
    }
}
