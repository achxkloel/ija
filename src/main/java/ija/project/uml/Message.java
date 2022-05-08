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
    private final String source;
    private final String target;

    public Message (String name, String type, String source, String target) {
        super(name);
        this.type = type;
        this.source = source;
        this.target = target;
    }

    public String getType () {
        return type;
    }

    public String getSource () {
        return source;
    }

    public String getTarget () {
        return target;
    }
}
