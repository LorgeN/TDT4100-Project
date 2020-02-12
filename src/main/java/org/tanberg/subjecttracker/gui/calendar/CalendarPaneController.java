package org.tanberg.subjecttracker.gui.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class CalendarPaneController {

    @FXML
    private AnchorPane handle;

    @FXML
    private Pane background;

    @FXML
    private Text dateDisplay;

    @FXML
    private Button addButton;

    private boolean enabled = true;
    private int year = -1;
    private int month = -1;
    private int date = -1;

    @FXML
    public void initialize() {
        this.background.setStyle("-fx-background-color: rgba(26,248,255,0.76);" +
                "-fx-background-insets: 3; " +
                "-fx-effect: dropshadow(three-pass-box, #000000, 5, 0, 0, 0);");
    }

    public void disable() {
        this.enabled = false;
        this.addButton.setDisable(true);

        this.background.setStyle("-fx-background-color: rgba(165,165,165,0.25);" +
                "-fx-background-insets: 3; " +
                "-fx-effect: dropshadow(three-pass-box, #000000, 5, 0, 0, 0);");
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
