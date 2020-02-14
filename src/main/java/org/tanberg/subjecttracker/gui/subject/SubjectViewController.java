package org.tanberg.subjecttracker.gui.subject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tanberg.subjecttracker.Manager;
import org.tanberg.subjecttracker.activity.Activity;
import org.tanberg.subjecttracker.activity.ActivityManager;
import org.tanberg.subjecttracker.gui.activity.ActivityListController;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.util.IconUtil;
import org.tanberg.subjecttracker.util.PopupUtil;

import java.util.List;
import java.util.stream.Collectors;

public class SubjectViewController {

    private static final String SUBJECT_MODIFY_FXML = "org/tanberg/subjecttracker/gui/subject/subjectmodify.fxml";
    private static final String ACTIVITY_LIST_FXML = "org/tanberg/subjecttracker/gui/activity/activitylist.fxml";

    @FXML
    private Pane background;

    @FXML
    private Button editButton;

    @FXML
    private Button viewButton;

    @FXML
    private Button statsButton;

    @FXML
    private Text title;

    @FXML
    private Text name;

    @FXML
    private Text semester;

    private Subject subject;
    private Stage stage;
    private SubjectListController listController;

    public void setSubject(Subject subject, Stage stage, SubjectListController listController) {
        this.subject = subject;
        this.stage = stage;
        this.listController = listController;

        Color color = this.subject.getColor();
        this.background.setStyle(String.format("-fx-background-color: rgb(%s, %s, %s, 0.25)", color.getRed() * 255, color.getGreen() * 255, color.getBlue() * 255));

        this.title.setText(subject.getCode());
        this.name.setText(subject.getFriendlyName());
        this.semester.setText(subject.getSemester().asString());

        this.editButton.setGraphic(IconUtil.getIconView("edit"));
        this.viewButton.setGraphic(IconUtil.getIconView("list"));
        this.statsButton.setGraphic(IconUtil.getIconView("stats"));
    }

    @FXML
    public void editSubject() {
        PopupUtil.<SubjectModifyController>createPopup(SUBJECT_MODIFY_FXML, this.stage, (popup, controller) -> {
            controller.fromSubject(this.subject);
            controller.setUp(popup, this.listController);
        });
    }

    @FXML
    public void viewActivities() {
        ActivityManager activityManager = Manager.getInstance().getActivityManager();

        PopupUtil.<ActivityListController>createPopup(ACTIVITY_LIST_FXML, this.stage, (popup, controller) -> {
            List<Activity> activities = activityManager.getActivities(this.subject).collect(Collectors.toList());
            controller.setup(popup, this.stage, this.subject.getFriendlyName(), activities);
            controller.lockSubject(this.subject);
            controller.render();
        });
    }

    @FXML
    public void viewStats() {

    }
}
