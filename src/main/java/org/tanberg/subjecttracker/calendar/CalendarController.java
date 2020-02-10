package org.tanberg.subjecttracker.calendar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Locale;

public class CalendarController {

    private int year;
    private int month;

    @FXML
    private GridPane grid;

    @FXML
    private Text monthTitle;

    @FXML
    public void initialize() {
    }

    @FXML
    public void onNextMonth() {
        int month = this.month + 1;
        int year = this.year;
        if (month > Calendar.DECEMBER) {
            month = Calendar.JANUARY;
            year++;
        }

        this.setTime(year, month);
    }

    @FXML
    public void onPreviousMonth() {
        int month = this.month - 1;
        int year = this.year;
        if (month < Calendar.JANUARY) {
            month = Calendar.DECEMBER;
            year--;
        }

        this.setTime(year, month);
    }

    public void setTime(int year, int month) {
        this.year = year;
        this.month = month;

        this.monthTitle.setText(this.getMonthName());
        this.grid.getChildren().clear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.year);
        calendar.set(Calendar.MONTH, this.month);
        calendar.add(Calendar.MONTH, -1);

        int prevDaysPerMonth = this.getLengthOfMonth(calendar);

        calendar.set(Calendar.DAY_OF_MONTH, prevDaysPerMonth);
        int finalWeek = calendar.get(Calendar.WEEK_OF_MONTH);

        calendar.set(Calendar.WEEK_OF_MONTH, finalWeek);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        int dateNow = calendar.get(Calendar.DAY_OF_MONTH);
        for (int day = dateNow - 1; day < prevDaysPerMonth; day++) {
            calendar.set(Calendar.DAY_OF_MONTH, day + 1);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int column = this.getColumn(dayOfWeek);
            if (column == -1) {
                continue;
            }

            try {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("calendarpane.fxml"));
                AnchorPane pane = loader.load();

                CalendarPaneController controller = loader.getController();
                controller.setTime(this.year, this.month - 1, day);
                controller.disable();

                this.grid.add(pane, column, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        calendar.add(Calendar.MONTH, 1);
        int daysPerMonth = this.getLengthOfMonth(calendar);

        int week = 0; // Store for later use with final column
        int dayOfWeek = 0;
        for (int day = 0; day < daysPerMonth; day++) {
            calendar.set(Calendar.DAY_OF_MONTH, day + 1);
            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int column = this.getColumn(dayOfWeek);
            if (column == -1) {
                continue;
            }

            week = calendar.get(Calendar.WEEK_OF_MONTH);

            try {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("calendarpane.fxml"));
                AnchorPane pane = loader.load();

                CalendarPaneController controller = loader.getController();
                controller.setTime(this.year, this.month, day);

                this.grid.add(pane, column, week - 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int startingWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        int requiredDaysOfNextMonth = 8 - dayOfWeek + (6 - week) * 7;
        calendar.add(Calendar.MONTH, 1);

        for (int day = 0; day < requiredDaysOfNextMonth; day++) {
            calendar.set(Calendar.DAY_OF_MONTH, day + 1);
            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int column = this.getColumn(dayOfWeek);
            if (column == -1) {
                continue;
            }

            int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
            int row = (week - 1) + (currentWeek - startingWeek);

            try {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("calendarpane.fxml"));
                AnchorPane pane = loader.load();

                CalendarPaneController controller = loader.getController();
                controller.setTime(this.year, this.month, day);
                controller.disable();

                this.grid.add(pane, column, row);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Internals

    private String getMonthName() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, this.month);
        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
    }

    private int getLengthOfMonth(Calendar calendar) {
        return YearMonth.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1).lengthOfMonth();
    }

    private int getColumn(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                return 0;
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
            default:
                return -1;
        }
    }
}
