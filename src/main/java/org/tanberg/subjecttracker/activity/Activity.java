package org.tanberg.subjecttracker.activity;

import org.tanberg.subjecttracker.subject.Subject;

import java.time.Instant;

public interface Activity {

    Instant getDate();

    default boolean isUpcoming() {
        return this.getDate().isAfter(Instant.now());
    }

    default boolean isExpired() {
        return this.getDate().isBefore(Instant.now());
    }

    void setDate(Instant date);

    Subject getSubject();

    double getCompletionState();

    default boolean isComplete() {
        return this.getCompletionState() == 1.0;
    }

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String desc);
}
