package org.tanberg.subjecttracker.subject;

import java.time.LocalDate;
import java.util.Calendar;
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

    public String asString() {
        return this.getSeason().getName() + " " + this.getYear();
    }

    public LocalDate getMinDate() {
        return LocalDate.of(this.year, this.getSeason().getMinMonth() + 1, 1);
    }

    public LocalDate getMaxDate() {
        return LocalDate.of(this.year, this.getSeason().getMaxMonth() + 1, 28);
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
        FALL("Fall", Calendar.AUGUST, Calendar.DECEMBER),
        SPRING("Spring", Calendar.JANUARY, Calendar.JUNE);

        private final String name;
        private final int minMonth;
        private final int maxMonth;

        SemesterSeason(String name, int minMonth, int maxMonth) {
            this.name = name;
            this.minMonth = minMonth;
            this.maxMonth = maxMonth;
        }

        public String getName() {
            return name;
        }

        public int getMinMonth() {
            return minMonth;
        }

        public int getMaxMonth() {
            return maxMonth;
        }
    }
}
