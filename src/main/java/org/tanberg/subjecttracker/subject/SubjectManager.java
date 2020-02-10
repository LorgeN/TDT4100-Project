package org.tanberg.subjecttracker.subject;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class SubjectManager {

    private final Map<String, Subject> subjectByCode;

    public SubjectManager() {
        this.subjectByCode = Maps.newConcurrentMap();

        Semester semester = new Semester(2020, Semester.SemesterSeason.SPRING);
        this.addSubject(new Subject("TFE4101", "Krets", semester, Color.BLUE));
        this.addSubject(new Subject("TDT4100", "OOP", semester, Color.GREEN));
    }

    public void load() {
        // TODO: Load from file
    }

    public void save() {
        // TODO: Save to file
    }

    public Collection<Subject> getSubjects() {
        return this.subjectByCode.values();
    }

    public Collection<Subject> search(String str) {
        return this.getSubjects().stream()
                .filter(subject -> StringUtils.containsIgnoreCase(subject.getCode(), str)
                        || StringUtils.containsIgnoreCase(subject.getFriendlyName(), str))
                .collect(Collectors.toUnmodifiableList());
    }

    public Subject getSubject(String code) {
        return this.subjectByCode.get(code);
    }

    public void addSubject(Subject subject) {
        this.subjectByCode.put(subject.getCode(), subject);
    }

    public void removeSubject(Subject subject) {
        this.subjectByCode.remove(subject.getCode());
    }
}
