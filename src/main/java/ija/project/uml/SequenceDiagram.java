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

    private final List<SequenceObject> sequenceObjects = new ArrayList<>();
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
    public void addSequenceObject(SequenceObject sequenceObject) {
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
     * Removes the message from the list of messages.
     *
     * @param messageToRemove message to remove.
     */
    public void removeMessage(Message messageToRemove) {
        this.messageList.remove(messageToRemove);
    }

    /**
     * Getter for the list of sequence objects.
     * @return list of sequence objects
     */
    public List<SequenceObject> getSequenceObjects() {
        return sequenceObjects;
    }

    /**
     * Getter for the list of messages
     * @return list of messages
     */
    public List<Message> getMessageList() {
        return messageList;
    }

    /**
     * Find object by his name.
     *
     * @param objectName name of object
     * @return instance of object or null.
     */
    public SequenceObject findSequenceObject (String objectName) {
        return this.sequenceObjects.stream().
                filter(U -> U.getName().equals(objectName)).
                findFirst().
                orElse(null);
    }
}
