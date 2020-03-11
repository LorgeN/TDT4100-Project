package org.tanberg.subjecttracker.storage.gson;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import javafx.scene.paint.Color;
import org.tanberg.subjecttracker.activity.Activity;
import org.tanberg.subjecttracker.activity.assignment.Assignment;
import org.tanberg.subjecttracker.storage.StorageManager;
import org.tanberg.subjecttracker.subject.Subject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class GSONStorageManager implements StorageManager {

    private static final String SUBJECT_FILE = "subjects.json";
    private static final String ACTIVITY_FILE = "activities.json";

    private final Gson gson;

    public GSONStorageManager() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Activity.class, new ActivityTypeAdapter())
                .registerTypeAdapter(Assignment.class, new ActivityTypeAdapter())
                .registerTypeAdapter(Color.class, new ColorTypeAdapter())
                .create();
    }

    @Override
    public void saveSubjects(Collection<Subject> subjects) {
        try (FileWriter fileWriter = new FileWriter(SUBJECT_FILE)) {
            this.gson.toJson(subjects, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveActivities(Collection<Activity> activities) {
        try (FileWriter fileWriter = new FileWriter(ACTIVITY_FILE)) {
            this.gson.toJson(activities, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Subject> loadSubjects() {
        if (!new File(SUBJECT_FILE).exists()) {
            return Lists.newArrayList();
        }

        try (FileReader fileReader = new FileReader(SUBJECT_FILE)) {
            Subject[] subjects = this.gson.fromJson(new JsonReader(fileReader), Subject[].class);
            return Lists.newArrayList(subjects);
        } catch (IOException e) {
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }

    @Override
    public Collection<Activity> loadActivities() {
        if (!new File(ACTIVITY_FILE).exists()) {
            return Lists.newArrayList();
        }

        try (FileReader fileReader = new FileReader(ACTIVITY_FILE)) {
            Activity[] activities = this.gson.fromJson(new JsonReader(fileReader), Activity[].class);
            return Lists.newArrayList(activities);
        } catch (IOException e) {
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }
}
