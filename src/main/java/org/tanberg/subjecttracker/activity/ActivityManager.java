package org.tanberg.subjecttracker.activity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class ActivityManager {

    private final List<Activity> activities;

    public ActivityManager() {
        this.activities = Lists.newArrayList();
    }

    public void load() {
        // TODO: Load from file
    }

    public void save() {
        // TODO: Save to file
    }

    public List<Activity> getActivities() {
        return ImmutableList.copyOf(this.activities);
    }

    public List<Activity> getUpcomingActivities() {
        return this.getActivities().stream().filter(Activity::isUpcoming).collect(Collectors.toList());
    }

    public List<Activity> getExpiredActivities() {
        return this.getActivities().stream().filter(Activity::isExpired).collect(Collectors.toList());
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }
}