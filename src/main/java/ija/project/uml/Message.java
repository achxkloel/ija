/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        Message.java
 * Description: Message class
 */

package ija.project.uml;

/**
 * Message between objects in the sequence diagram.
 */
public class Message extends Element {

    private final String type;
    private final String source;
    private final String target;

    /**
     * Message constructor
     * @param name name of the message (displayed as description)
     * @param type type of the message (synchronous, asynchronous...)
     * @param source the message goes from this object
     * @param target the message goes to this object
     */
    public Message (String name, String type, String source, String target) {
        super(name);
        this.type = type;
        this.source = source;
        this.target = target;
    }

    /**
     * Getter for the type
     * @return type of the message
     */
    public String getType () {
        return type;
    }

    /**
     * Getter for the source
     * @return source of the message
     */
    public String getSource () {
        return source;
    }

    /**
     * Getter for the target
     * @return target of the message
     */
    public String getTarget () {
        return target;
    }
}
