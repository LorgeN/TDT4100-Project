package org.tanberg.subjecttracker.subject;

import javafx.scene.paint.Color;

import java.util.Objects;

public class Subject {

    private String code;
    private Semester semester;
    private String friendlyName;
    private Color color;

    public Subject(String code, String friendlyName, Semester semester, Color color) {
        this.code = code;
        this.friendlyName = friendlyName;
        this.semester = semester;
        this.color = color;
    }

    public String getCode() {
        return code;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public Semester getSemester() {
        return semester;
    }

    public Color getColor() {
        return color;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Subject subject = (Subject) o;
        return Objects.equals(code, subject.code) &&
                Objects.equals(semester, subject.semester) &&
                Objects.equals(friendlyName, subject.friendlyName) &&
                Objects.equals(color, subject.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, semester, friendlyName, color);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "code='" + code + '\'' +
                ", semester=" + semester +
                ", friendlyName='" + friendlyName + '\'' +
                ", color=" + color +
                '}';
    }
}
