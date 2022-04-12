package ija.project.uml;

import java.util.ArrayList;
import java.util.List;

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
