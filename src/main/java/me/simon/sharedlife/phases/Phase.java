package me.simon.sharedlife.phases;

public class Phase {

    // Nom court
    private final String name;

    // Description
    private final String description;

    // Complété
    private boolean completed;

    public Phase(
            String name,
            String description
    ) {

        this.name = name;
        this.description = description;

        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(
            boolean completed
    ) {

        this.completed = completed;
    }
}