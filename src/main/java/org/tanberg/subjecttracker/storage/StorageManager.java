package org.tanberg.subjecttracker.storage;

import org.tanberg.subjecttracker.activity.Activity;
import org.tanberg.subjecttracker.subject.Subject;

import java.util.Collection;

public interface StorageManager {

    void saveSubjects(Collection<Subject> subjects);

    void saveActivities(Collection<Activity> activities);

    Collection<Subject> loadSubjects();

    Collection<Activity> loadActivities();
}
