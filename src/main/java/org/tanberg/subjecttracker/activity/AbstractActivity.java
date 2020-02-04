package org.tanberg.subjecttracker.activity;

import org.tanberg.subjecttracker.subject.Subject;

import java.time.Instant;

public abstract class AbstractActivity implements Activity {

    private Subject subject;
    private Instant date;
    private String title;
    private String description;

    public AbstractActivity(Subject subject, Instant date, String title, String description) {
        this.subject = subject;
        this.date = date;
        this.title = title;
        this.description = description;
    }

    @Override
    public Instant getDate() {
        return this.date;
    }

    @Override
    public void setDate(Instant date) {
        this.date = date;
    }

    @Override
    public Subject getSubject() {
        return this.subject;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
