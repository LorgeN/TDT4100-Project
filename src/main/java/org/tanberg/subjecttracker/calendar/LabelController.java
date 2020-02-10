package org.tanberg.subjecttracker.calendar;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class LabelController {

    @FXML
    private Text text;

    public void setText(String text) {
        this.text.setText(text);
    }
}
