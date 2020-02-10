package org.tanberg.subjecttracker.calendar;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class CalendarPaneController {

    @FXML
    private Pane background;

    @FXML
    private Text dateDisplay;

    private boolean enabled = true;
    private int year = -1;
    private int month = -1;
    private int date = -1;

    @FXML
    public void initialize() {
        this.background.setStyle("-fx-background-color: rgba(126,255,0,0.35)");
    }

    public void disable() {
        this.enabled = false;

        this.background.setStyle("-fx-background-color: rgba(165,165,165,0.25)");
        this.dateDisplay.setOpacity(0.35);
    }

    public void setTime(int year, int month, int date) {
        this.year = year;
        this.month = month;
        this.date = date;

        this.dateDisplay.setText(String.valueOf(this.date + 1));

        // TODO: Display today's activities
    }
}
