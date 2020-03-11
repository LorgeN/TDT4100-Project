package org.tanberg.subjecttracker.subject;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.StringUtils;
import org.tanberg.subjecttracker.Manager;
import org.tanberg.subjecttracker.activity.ActivityManager;
import org.tanberg.subjecttracker.storage.StorageManager;
import org.tanberg.subjecttracker.util.Listenable;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SubjectManager extends Listenable {

    private final StorageManager storageManager;
    private final List<Subject> subjects;

    public SubjectManager(StorageManager storageManager) {
        this.storageManager = storageManager;
        this.subjects = Lists.newArrayList();
    }

    public void load() {
        this.subjects.clear();
        this.subjects.addAll(this.storageManager.loadSubjects());

        if (!this.subjects.isEmpty()) {
            return;
        }

        Semester semester = new Semester(2020, Semester.SemesterSeason.SPRING);
        this.addSubject(new Subject("TFE4101", "Krets", semester, Color.BLUE));
        this.addSubject(new Subject("TDT4100", "OOP", semester, Color.GREEN));
        this.addSubject(new Subject("TMA4115", "Matte 3", semester, Color.RED));
        this.addSubject(new Subject("TDT4180", "MMI", semester, Color.PURPLE));
    }

    public void save() {
        this.storageManager.saveSubjects(this.getSubjects());
    }

    public Collection<Subject> getSubjects() {
        return ImmutableList.copyOf(this.subjects);
    }

    public Collection<Subject> search(String str) {
        return this.getSubjects().stream()
                .filter(subject -> StringUtils.containsIgnoreCase(subject.getCode(), str)
                        || StringUtils.containsIgnoreCase(subject.getFriendlyName(), str))
                .collect(Collectors.toUnmodifiableList());
    }

    public Subject getSubject(String code) {
        return this.subjects.stream()
                .filter(subject -> subject.getCode().equalsIgnoreCase(code))
                .findFirst().orElse(null);
    }

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
        this.save();
        this.runListeners();
    }

    public void removeSubject(Subject subject) {
        this.subjects.remove(subject);
        this.save();

        ActivityManager activityManager = Manager.getInstance().getActivityManager();
        activityManager.getActivities(subject).forEach(activityManager::removeActivity);

        this.runListeners();
    }
}
