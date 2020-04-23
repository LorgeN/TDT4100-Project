package org.tanberg.subjecttracker.activity;

import org.tanberg.subjecttracker.subject.Subject;

import java.time.Instant;
import java.util.Objects;

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
    public void setSubject(Subject subject) {
        this.subject = subject;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof AbstractActivity)) {
            return false;
        }

        AbstractActivity activity = (AbstractActivity) o;
        return subject.equals(activity.subject) &&
          date.equals(activity.date) &&
          title.equals(activity.title) &&
          description.equals(activity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, date, title, description);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
          "subject=" + subject +
          ", date=" + date +
          ", title='" + title + '\'' +
          ", description='" + description + '\'' +
          '}';
    }
}
