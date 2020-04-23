package org.tanberg.test;

import com.google.common.collect.Lists;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import org.tanberg.subjecttracker.activity.Activity;
import org.tanberg.subjecttracker.activity.ActivityManager;
import org.tanberg.subjecttracker.activity.assignment.Assignment;
import org.tanberg.subjecttracker.storage.gson.GSONStorageManager;
import org.tanberg.subjecttracker.subject.Semester;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.subject.SubjectManager;

import java.time.Instant;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class GSONFileStorageTest {

    private GSONStorageManager manager;
    private SubjectManager subjectManager;
    private ActivityManager activityManager;

    private List<Subject> subjects;
    private List<Activity> activities;

    @Before
    public void setUp() throws Exception {
        this.manager = new GSONStorageManager();

        this.subjectManager = new SubjectManager(this.manager);
        this.activityManager = new ActivityManager(this.manager);

        this.manager.setUp(this.subjectManager);

        this.subjects = Lists.newArrayList();

        Semester semester = new Semester(2020, Semester.SemesterSeason.SPRING);
        this.subjects.add(new Subject("EKS0001", "Eksempel 1", semester, Color.BLUE));
        this.subjects.add(new Subject("EKS0002", "Eksempel 2", semester, Color.GREEN));
        this.subjects.add(new Subject("EKS0003", "Eksempel 3", semester, Color.RED));
        this.subjects.add(new Subject("EKS0004", "Eksempel 4", semester, Color.PURPLE));

        // Add directly without saving
        this.subjectManager.getSubjectsMutable().addAll(this.subjects);

        // Use this instead of Instant.now() because that considers nano-sec accuracy
        Instant now = Instant.ofEpochMilli(System.currentTimeMillis());

        this.activities = Lists.newArrayList();
        this.activities.add(new Assignment(this.subjects.get(0), now, "Eks 1", "Dette er et eksempel"));
        this.activities.add(new Assignment(this.subjects.get(1), now, "Eks 2", "Dette er et eksempel"));
        this.activities.add(new Assignment(this.subjects.get(2), now, "Eks 3", "Dette er et eksempel"));
        this.activities.add(new Assignment(this.subjects.get(3), now, "Eks 4", "Dette er et eksempel"));

        this.activityManager.getActivitesMutable().addAll(this.activities);
    }

    @Test
    public void testStoreActivity() {
        assertThat(this.activityManager.getActivities()).containsExactly(this.activities.toArray());
        this.activityManager.save();

        assertThat(this.activityManager.getActivities()).containsExactly(this.activities.toArray());

        this.activityManager.load();
        assertThat(this.activityManager.getActivities()).containsExactly(this.activities.toArray());

        ActivityManager manager = new ActivityManager(this.manager);
        manager.load();
        assertThat(manager.getActivities()).containsExactly(this.activities.toArray());
    }

    @Test
    public void testStoreSubject() {
        assertThat(this.subjectManager.getSubjects()).containsExactly(this.subjects.toArray());
        this.subjectManager.save();

        assertThat(this.subjectManager.getSubjects()).containsExactly(this.subjects.toArray());

        this.subjectManager.load();
        assertThat(this.subjectManager.getSubjects()).containsExactly(this.subjects.toArray());

        SubjectManager manager = new SubjectManager(this.manager);
        manager.load();
        assertThat(manager.getSubjects()).containsExactly(this.subjects.toArray());
    }
}
