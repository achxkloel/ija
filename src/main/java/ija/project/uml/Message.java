/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        Message.java
 * Description: Message class
 */

package ija.project.uml;

/**
 * Message between participants.
 */
public class Message extends Element {

    private final String type;
    private final Participant source;
    private final Participant target;
    private final String description;

    public Message (String name, String type, Participant source, Participant target, String description) {
        super(name);
        this.type = type;
        this.source = source;
        this.target = target;
        this.description = description;
    }

    public String getType () {
        return type;
    }

    public Participant getSource () {
        return source;
    }

    public Participant getTarget () {
        return target;
    }

    public String getDescription () {
        return description;
    }
}
