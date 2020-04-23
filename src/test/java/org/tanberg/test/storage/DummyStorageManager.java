package org.tanberg.test.storage;

import org.tanberg.subjecttracker.activity.Activity;
import org.tanberg.subjecttracker.storage.StorageManager;
import org.tanberg.subjecttracker.subject.Subject;

import java.util.Collection;

/**
 * Storage manager that doesn't actually write to any files for testing purposes
 */
public class DummyStorageManager implements StorageManager {

    private Collection<Subject> subjects;
    private Collection<Activity> activities;

    @Override
    public void saveSubjects(Collection<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public void saveActivities(Collection<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public Collection<Subject> loadSubjects() {
        return this.subjects;
    }

    @Override
    public Collection<Activity> loadActivities() {
        return this.activities;
    }
}
