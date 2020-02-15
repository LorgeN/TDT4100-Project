package org.tanberg.subjecttracker.gui.statistics;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.subject.statistics.SubjectStatistics;
import org.tanberg.subjecttracker.util.IconUtil;

public class SubjectStatisticsViewController {

    @FXML
    private Button closeButton;

    @FXML
    private Text title;

    @FXML
    private Text assignmentsTotal;

    @FXML
    private Text assignmentsCompleted;

    private Popup popup;

    @FXML
    public void initialize() {
        this.closeButton.setGraphic(IconUtil.getIconView("close"));
    }

    @FXML
    public void close() {
        this.popup.hide();
    }

    public void setUp(Subject subject, Popup popup) {
        this.popup = popup;

        this.title.setText(subject.getFriendlyName());

        SubjectStatistics statistics = new SubjectStatistics(subject);
        this.assignmentsTotal.setText(String.valueOf(statistics.getTotalAssignments()));
        this.assignmentsCompleted.setText(String.valueOf(statistics.getCompletedAssignments()));
    }
}
