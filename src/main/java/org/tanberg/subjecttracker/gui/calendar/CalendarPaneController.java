package org.tanberg.subjecttracker.gui.calendar;

import com.jfoenix.controls.JFXRippler;
import com.jfoenix.effects.JFXDepthManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
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

    private boolean enabled = true;
    private int year = -1;
    private int month = -1;
    private int date = -1;

    @FXML
    public void initialize() {
        this.background.setStyle("-fx-background-color: rgba(126,255,0,0.35)");

        Label label = new Label("TEST");
        label.setStyle("-fx-background-color: WHITE");
        label.setPadding(new Insets(20));
        JFXRippler rippler = new JFXRippler(label);
        this.handle.getChildren().add(rippler);
        JFXDepthManager.setDepth(rippler, 1);
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
