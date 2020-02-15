package org.tanberg.subjecttracker.gui.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tanberg.subjecttracker.Manager;
import org.tanberg.subjecttracker.activity.Activity;
import org.tanberg.subjecttracker.activity.ActivityManager;
import org.tanberg.subjecttracker.gui.activity.ActivityListController;
import org.tanberg.subjecttracker.gui.activity.ActivityModifyController;
import org.tanberg.subjecttracker.util.PopupUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CalendarPaneController {

    private static final String ACTIVITY_MODIFY_FXML = "org/tanberg/subjecttracker/gui/activity/activitymodify.fxml";
    private static final String ACTIVITY_LIST_FXML = "org/tanberg/subjecttracker/gui/activity/activitylist.fxml";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private Button listButton;

    @FXML
    private Pane background;

    @FXML
    private Text dateDisplay;

    @FXML
    private Button addButton;

    private Stage stage;
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

    @FXML
    public void viewActivityList() {
        if (!this.enabled) {
            return;
        }

        ActivityManager activityManager = Manager.getInstance().getActivityManager();
        List<Activity> activities = activityManager.getActivities(this.year, this.month, this.date);

        LocalDate date = LocalDate.of(this.year, this.month + 1, this.date + 1);

        PopupUtil.<ActivityListController>createPopup(ACTIVITY_LIST_FXML, this.stage, (popup, controller) -> {
            controller.setup(popup, this.stage, date.format(FORMATTER), activities);
            controller.render();
        });
    }

    @FXML
    public void createNewActivity() {
        if (!this.enabled) {
            return;
        }

        PopupUtil.<ActivityModifyController>createPopup(ACTIVITY_MODIFY_FXML, this.stage, (popup, controller) -> {
            controller.setUp(popup, (activity, aBoolean) -> {
            });
            controller.setDate(LocalDateTime.of(LocalDate.of(this.year, this.month + 1, this.date + 1), LocalTime.now()));
        });
    }

    public void disable() {
        this.enabled = false;
        this.addButton.setDisable(true);
        this.listButton.setDisable(true);

        this.background.setStyle("-fx-background-color: rgba(165,165,165,0.25);" +
                "-fx-background-insets: 3; " +
                "-fx-effect: dropshadow(three-pass-box, #000000, 5, 0, 0, 0);");
        this.dateDisplay.setOpacity(0.35);
    }

    public void setTime(Stage stage, int year, int month, int date) {
        this.stage = stage;
        this.year = year;
        this.month = month;
        this.date = date;

        this.dateDisplay.setText(String.valueOf(this.date + 1));

        ActivityManager activityManager = Manager.getInstance().getActivityManager();
        List<Activity> activities = activityManager.getActivities(year, month, date);
        if (activities.isEmpty()) {
            this.listButton.setVisible(false);
            return;
        }

        this.listButton.setText(activities.size() + " " + (activities.size() == 1 ? "activity" : "activities"));
    }
}
