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
