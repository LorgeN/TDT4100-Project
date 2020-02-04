package org.tanberg.subjecttracker.activity.assignment;

import org.tanberg.subjecttracker.activity.AbstractActivity;
import org.tanberg.subjecttracker.subject.Subject;

import java.time.Instant;

public class Assignment extends AbstractActivity {

    private boolean complete;

    public Assignment(Subject subject, Instant date, String title, String description) {
        super(subject, date, title, description);
    }

    @Override
    public double getCompletionState() {
        return this.complete ? 1.0 : 0.0;
    }

    public void setComplete() {
        this.complete = true;
    }
}
