package org.tanberg.subjecttracker.subject;

import java.util.Objects;

public class Semester {

    private final int year;
    private final SemesterSeason season;

    public Semester(int year, SemesterSeason season) {
        this.year = year;
        this.season = season;
    }

    public int getYear() {
        return year;
    }

    public SemesterSeason getSeason() {
        return season;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Semester semester = (Semester) o;
        return year == semester.year &&
                season == semester.season;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, season);
    }

    @Override
    public String toString() {
        return "Semester{" +
                "year=" + year +
                ", season=" + season +
                '}';
    }

    public enum SemesterSeason {
        FALL,
        SPRING
    }
}
