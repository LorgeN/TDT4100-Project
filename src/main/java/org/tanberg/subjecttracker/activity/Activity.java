package org.tanberg.subjecttracker.activity;

import org.tanberg.subjecttracker.subject.Subject;

import java.time.Instant;
import java.util.Comparator;

public interface Activity extends Comparable<Activity> {

    Comparator<Activity> COMPARATOR = Comparator.comparing(Activity::getDate).reversed();

    @Override
    default int compareTo(Activity o) {
        return COMPARATOR.compare(this, o);
    }

    Instant getDate();

    default boolean isUpcoming() {
        return this.getDate().isAfter(Instant.now());
    }

    default boolean isExpired() {
        return this.getDate().isBefore(Instant.now());
    }

    void setDate(Instant date);

    Subject getSubject();

    void setSubject(Subject subject);

    double getCompletionState();

    default boolean isComplete() {
        return this.getCompletionState() == 1.0;
    }

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String desc);
}
