package org.tanberg.subjecttracker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import org.tanberg.subjecttracker.calendar.CalendarController;

import java.io.IOException;
import java.net.URL;

public class ParentController {

    @FXML
    private Tab calendarTab;

    @FXML
    private Tab overviewPane;

    @FXML
    public void initialize() throws IOException {
        URL resource = this.getClass().getResource("calendar/calendar.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        AnchorPane calendarPane = loader.load();

        CalendarController controller = loader.getController();
        controller.setTime(2020, 0);

        this.calendarTab.setContent(calendarPane);
    }
}
