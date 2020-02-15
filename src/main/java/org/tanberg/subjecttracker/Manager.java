package org.tanberg.subjecttracker;

import org.tanberg.subjecttracker.activity.ActivityManager;
import org.tanberg.subjecttracker.subject.SubjectManager;

public class Manager {

    private static final Manager MANAGER = new Manager();

    public static Manager getInstance() {
        return MANAGER;
    }

    private final SubjectManager subjectManager;
    private final ActivityManager activityManager;

    private Manager() {
        this.subjectManager = new SubjectManager();
        this.activityManager = new ActivityManager();
    }

    public SubjectManager getSubjectManager() {
        return subjectManager;
    }

    public ActivityManager getActivityManager() {
        return activityManager;
    }
}
