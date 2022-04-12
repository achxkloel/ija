package ija.project.uml;

/**
 * General element.
 */
public class Element {

    /**
     * Name of element.
     */
    private String name;

    /**
     * Create new element.
     *
     * @param name name of element.
     */
    public Element (String name) {
        this.name = name;
    }

    /**
     * Get name of element.
     *
     * @return current name.
     */
    public String getName () {
        return this.name;
    }

    /**
     * Set name of element.
     *
     * @param newName new name.
     */
    public void setName (String newName) {
        this.name = newName;
    }
}
