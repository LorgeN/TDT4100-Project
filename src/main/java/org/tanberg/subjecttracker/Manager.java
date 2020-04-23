package org.tanberg.subjecttracker;

import org.tanberg.subjecttracker.activity.ActivityManager;
import org.tanberg.subjecttracker.storage.gson.GSONStorageManager;
import org.tanberg.subjecttracker.subject.SubjectManager;

public class Manager {

    private static final Manager MANAGER;

    static {
        MANAGER = new Manager();
        MANAGER.getSubjectManager().load();
        MANAGER.getActivityManager().load();
    }

    public static Manager getInstance() {
        return MANAGER;
    }

    private final SubjectManager subjectManager;
    private final ActivityManager activityManager;

    private Manager() {
        GSONStorageManager storageManager = new GSONStorageManager();

        this.subjectManager = new SubjectManager(storageManager);
        this.activityManager = new ActivityManager(storageManager);

        storageManager.setUp(this.subjectManager);
    }

    public SubjectManager getSubjectManager() {
        return subjectManager;
    }

    public ActivityManager getActivityManager() {
        return activityManager;
    }
}
