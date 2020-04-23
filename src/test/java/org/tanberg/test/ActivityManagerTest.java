package org.tanberg.test;

import com.google.common.collect.Lists;
import com.google.common.truth.Truth;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import org.tanberg.subjecttracker.activity.Activity;
import org.tanberg.subjecttracker.activity.ActivityManager;
import org.tanberg.subjecttracker.activity.assignment.Assignment;
import org.tanberg.subjecttracker.storage.StorageManager;
import org.tanberg.subjecttracker.storage.gson.GSONStorageManager;
import org.tanberg.subjecttracker.subject.Semester;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.subject.SubjectManager;
import org.tanberg.test.storage.DummyStorageManager;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.*;

public class ActivityManagerTest {

    private ActivityManager activityManager;

    private List<Subject> subjects;
    private List<Activity> activities;

    @Before
    public void setUp() throws Exception {
        StorageManager manager = new DummyStorageManager();

        SubjectManager subjectManager = new SubjectManager(manager);
        this.activityManager = new ActivityManager(manager);

        this.subjects = Lists.newArrayList();

        Semester semester = new Semester(2020, Semester.SemesterSeason.SPRING);
        this.subjects.add(new Subject("EKS0001", "Eksempel 1", semester, Color.BLUE));
        this.subjects.add(new Subject("EKS0002", "Eksempel 2", semester, Color.GREEN));
        this.subjects.add(new Subject("EKS0003", "Eksempel 3", semester, Color.RED));
        this.subjects.add(new Subject("EKS0004", "Eksempel 4", semester, Color.PURPLE));

        // Add directly without saving
        subjectManager.getSubjectsMutable().addAll(this.subjects);

        long milli = System.currentTimeMillis();
        long day = TimeUnit.DAYS.toMillis(3);

        ZonedDateTime time = ZonedDateTime.of(2000, 6, 25, 12, 0, 0, 0, ZoneId.systemDefault());

        this.activities = Lists.newArrayList();
        this.activities.add(new Assignment(this.subjects.get(0), Instant.from(time), "Eks 1", "Dette er et eksempel"));
        this.activities.add(new Assignment(this.subjects.get(1), Instant.ofEpochMilli(milli - day), "Eks 2", "Dette er et eksempel"));
        this.activities.add(new Assignment(this.subjects.get(0), Instant.ofEpochMilli(milli + day), "Eks 3", "Dette er et eksempel"));
        this.activities.add(new Assignment(this.subjects.get(3), Instant.ofEpochMilli(milli + day), "Eks 4", "Dette er et eksempel"));

        this.activityManager.getActivitesMutable().addAll(this.activities);
    }

    @Test
    public void getActivities() {
        assertThat(this.activityManager.getActivities()).containsExactly(this.activities.toArray());
        assertThat(this.activityManager.getActivities(this.subjects.get(0)).collect(Collectors.toList()))
          .containsExactly(this.activities.get(0), this.activities.get(2));
        assertThat(this.activityManager.getActivities(2000, 5, 24)).containsExactly(this.activities.get(0));
    }

    @Test
    public void getUpcomingActivities() {
        assertThat(this.activityManager.getUpcomingActivities()).containsExactly(this.activities.get(2), this.activities.get(3));
    }

    @Test
    public void getExpiredActivities() {
        assertThat(this.activityManager.getExpiredActivities()).containsExactly(this.activities.get(0), this.activities.get(1));
    }

    @Test
    public void addActivity() {
        Subject subject = this.subjects.get(0);
        Assignment test = new Assignment(subject, Instant.now(), "Test 1", "Dette er en test");

        this.activities.add(test);
        this.activityManager.addActivity(test);

        assertThat(this.activityManager.getActivities()).containsExactly(this.activities.toArray());
    }

    @Test
    public void removeActivity() {
        Activity activity = this.activities.get(0);

        this.activities.remove(activity);
        this.activityManager.removeActivity(activity);

        assertThat(this.activityManager.getActivities()).containsExactly(this.activities.toArray());
    }
}