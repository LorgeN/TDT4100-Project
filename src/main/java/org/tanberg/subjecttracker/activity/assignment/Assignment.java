package org.tanberg.subjecttracker.activity.assignment;

import org.tanberg.subjecttracker.activity.AbstractActivity;
import org.tanberg.subjecttracker.subject.Subject;

import java.time.Instant;
import java.util.Objects;

public class Assignment extends AbstractActivity {

    private boolean complete;

    public Assignment(Subject subject, Instant date, String title, String description) {
        super(subject, date, title, description);
    }

    public Assignment(Subject subject, Instant date, String title, String description, boolean complete) {
        super(subject, date, title, description);
        this.complete = complete;
    }

    @Override
    public double getCompletionState() {
        return this.complete ? 1.0 : 0.0;
    }

    public void setComplete(boolean val) {
        this.complete = val;
    }
}
