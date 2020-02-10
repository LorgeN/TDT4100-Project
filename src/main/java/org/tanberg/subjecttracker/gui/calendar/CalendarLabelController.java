package org.tanberg.subjecttracker.gui.calendar;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class CalendarLabelController {

    @FXML
    private Text text;

    public void setText(String text) {
        this.text.setText(text);
    }
}
