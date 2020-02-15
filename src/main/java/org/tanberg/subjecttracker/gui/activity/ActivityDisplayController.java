package org.tanberg.subjecttracker.gui.activity;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.tanberg.subjecttracker.activity.Activity;
import org.tanberg.subjecttracker.activity.assignment.Assignment;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.util.IconUtil;
import org.tanberg.subjecttracker.util.PopupUtil;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class ActivityDisplayController {

    private static final String ACTIVITY_MODIFY_FXML = "org/tanberg/subjecttracker/gui/activity/activitymodify.fxml";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withZone(ZoneId.systemDefault())
            .withLocale(Locale.getDefault());

    @FXML
    private Pane background;

    @FXML
    private Text title;

    @FXML
    private Text subtitle;

    @FXML
    private Button editButton;

    @FXML
    private ToggleButton completeButton;

    private Activity activity;
    private Stage stage;
    private ActivityListController listController;
    private Subject subject;

    @FXML
    public void initialize() {
        this.editButton.setGraphic(IconUtil.getIconView("edit"));
        this.completeButton.selectedProperty().addListener((value, oldVal, newVal) -> ((Assignment) this.activity).setComplete(newVal));
    }

    public void setActivity(Stage stage, Activity activity, ActivityListController controller) {
        this.stage = stage;
        this.activity = activity;
        this.listController = controller;
        this.title.setText(activity.getTitle());

        Tooltip tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.seconds(1));
        tooltip.setWrapText(true);
        tooltip.setWidth(300.0d);
        tooltip.setTextAlignment(TextAlignment.LEFT);
        tooltip.setText(activity.getDescription());
        Tooltip.install(this.title, tooltip);

        this.subtitle.setText(activity.getSubject().getFriendlyName() + " - " + FORMATTER.format(activity.getDate()));

        Subject subject = activity.getSubject();

        Color color = subject.getColor();
        this.background.setStyle(String.format("-fx-background-color: rgb(%s, %s, %s, 0.25)", color.getRed() * 255, color.getGreen() * 255, color.getBlue() * 255));

        this.completeButton.setSelected(activity.isComplete());
    }

    public void lockSubject(Subject subject) {
        this.subject = subject;
    }

    @FXML
    public void editActivity() {
        this.editButton.setDisable(true);

        PopupUtil.<ActivityModifyController>createPopup(ACTIVITY_MODIFY_FXML, this.stage, (popup, controller) -> {
            controller.setUp(popup, (activity, remove) -> this.listController.rerender(activity, remove));
            controller.fromActivity(this.activity);
            controller.lockSubject(this.subject);
        });
    }
}
