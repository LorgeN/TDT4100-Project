package org.tanberg.subjecttracker.activity.goal;

public class PartialGoal {

    private String description;
    private boolean complete;

    public PartialGoal(String description, boolean complete) {
        this.description = description;
        this.complete = complete;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
