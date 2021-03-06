package org.tanberg.subjecttracker.activity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.tanberg.subjecttracker.storage.StorageManager;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.util.Listenable;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ActivityManager extends Listenable {

    private final StorageManager storageManager;
    private final List<Activity> activities;

    public ActivityManager(StorageManager storageManager) {
        this.storageManager = storageManager;
        this.activities = Lists.newArrayList();
    }

    public void load() {
        this.activities.clear();
        this.activities.addAll(this.storageManager.loadActivities());
    }

    public void save() {
        this.storageManager.saveActivities(this.getActivities());
    }

    public List<Activity> getActivities() {
        return ImmutableList.copyOf(this.activities);
    }

    public Collection<Activity> getActivitesMutable() {
        return this.activities;
    }

    public Stream<Activity> getActivities(Subject subject) {
        return this.getActivities().stream().sorted()
                .filter(activity -> activity.getSubject().equals(subject));
    }

    public List<Activity> getUpcomingActivities() {
        return this.getActivities().stream().sorted()
                .filter(Activity::isUpcoming).collect(Collectors.toList());
    }

    public List<Activity> getExpiredActivities() {
        return this.getActivities().stream().sorted()
                .filter(Activity::isExpired).collect(Collectors.toList());
    }

    public List<Activity> getActivities(int year, int month, int date) {
        return this.getActivities().stream().sorted().filter(activity -> {
            Instant activityInstant = activity.getDate();
            ZonedDateTime dateTime = ZonedDateTime.ofInstant(activityInstant, ZoneId.systemDefault());

            int activityYear = dateTime.get(ChronoField.YEAR);
            int activityMonth = dateTime.get(ChronoField.MONTH_OF_YEAR) - 1;
            int activityDate = dateTime.get(ChronoField.DAY_OF_MONTH) - 1;
            return activityYear == year && activityMonth == month && activityDate == date;
        }).collect(Collectors.toList());
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
        this.save();
        this.runListeners();
    }

    public void removeActivity(Activity activity) {
        this.activities.remove(activity);
        this.save();
        this.runListeners();
    }
}
