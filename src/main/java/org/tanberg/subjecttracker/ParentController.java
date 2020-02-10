package org.tanberg.subjecttracker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import org.tanberg.subjecttracker.gui.calendar.CalendarController;

import java.io.IOException;

public class ParentController {

    @FXML
    private SplitPane contentPane;

    @FXML
    public void initialize() throws IOException {
        FXMLLoader calendarLoader = new FXMLLoader(this.getClass().getResource("gui/calendar/calendar.fxml"));
        AnchorPane calendarPane = calendarLoader.load();

        FXMLLoader subjectListLoader = new FXMLLoader(this.getClass().getResource("gui/subject/subjectlistview.fxml"));
        AnchorPane subjectListPane = subjectListLoader.load();

        CalendarController controller = calendarLoader.getController();
        controller.setTimeNow();

        this.contentPane.getItems().add(subjectListPane);
        this.contentPane.getItems().add(calendarPane);
    }
}
