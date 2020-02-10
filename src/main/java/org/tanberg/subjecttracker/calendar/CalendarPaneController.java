package org.tanberg.subjecttracker.calendar;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class CalendarPaneController {

    @FXML
    private Pane background;

    @FXML
    private Text dateDisplay;

    @FXML
    private Text activityDisplay;

    private boolean enabled = true;
    private int year = -1;
    private int month = -1;
    private int date = -1;

    @FXML
    public void initialize() {
        if (this.month == -1) {
            return;
        }

        this.dateDisplay.setText(String.valueOf(this.date + 1));
    }

    public void disable() {
        this.enabled = false;

        this.background.setStyle("-fx-background-color: rgb(165,165,165)");
        this.dateDisplay.setOpacity(0.5);
    }

    public void setTime(int year, int month, int date) {
        this.year = year;
        this.month = month;
        this.date = date;

        this.dateDisplay.setText(String.valueOf(this.date + 1));

        // TODO: Display today's activities
    }
}
