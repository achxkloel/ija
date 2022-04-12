/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        Action.java
 * Description: Action class
 */

package ija.project.uml;

public class Action extends Element {

    private final Participant target;

    public Action (String name, Participant target) {
        super(name);
        this.target = target;
    }

    public Participant getTarget () {
        return target;
    }
}
