package org.tanberg.subjecttracker.subject;

public class Subject {

    private final String code;
    private final Semester semester;
    private String friendlyName;
    private String description;

    public Subject(String code, String friendlyName, String description, Semester semester) {
        this.code = code;
        this.friendlyName = friendlyName;
        this.description = description;
        this.semester = semester;
    }

    public String getCode() {
        return code;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public String getDescription() {
        return description;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
