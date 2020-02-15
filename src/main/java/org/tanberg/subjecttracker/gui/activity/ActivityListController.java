package org.tanberg.subjecttracker.gui.activity;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.tanberg.subjecttracker.activity.Activity;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.util.IconUtil;
import org.tanberg.subjecttracker.util.PopupUtil;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class ActivityListController {

    private static final String ACTIVITY_MODIFY_FXML = "org/tanberg/subjecttracker/gui/activity/activitymodify.fxml";

    @FXML
    private Button closeButton;

    @FXML
    private Text header;

    @FXML
    private JFXListView<AnchorPane> list;

    private Popup popup;
    private Stage stage;
    private List<Activity> activities;
    private Subject subject;

    @FXML
    public void initialize() {
        this.closeButton.setGraphic(IconUtil.getIconView("close"));

        this.list.setExpanded(true);
        this.list.setFocusTraversable(false);
    }

    @FXML
    public void close() {
        this.popup.hide();
    }

    @FXML
    public void createNew() {
        this.close();

        double x = this.popup.getX();
        double y = this.popup.getY();
        PopupUtil.<ActivityModifyController>createPopup(ACTIVITY_MODIFY_FXML, x, y, this.stage, (popup, controller) -> {
            controller.setUp(popup, this::rerender);
            controller.lockSubject(this.subject);
        });
    }

    public void rerender(Activity activity, boolean remove) {
        this.popup.show(this.stage);
        this.list.getItems().clear();

        if (activity != null) {
            if (remove) {
                this.activities.remove(activity);
            } else {
                this.activities.add(activity);
            }

            this.activities.sort(Comparator.naturalOrder());
        }

        this.render();
    }

    public void setup(Popup popup, Stage stage, String header, List<Activity> activities) {
        this.popup = popup;
        this.stage = stage;
        this.header.setText(header);
        this.activities = activities;
    }

    public void lockSubject(Subject subject) {
        this.subject = subject;
    }

    public void render() {
        for (Activity activity : this.activities) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("activitydisplay.fxml"));

            try {
                AnchorPane pane = loader.load();
                ActivityDisplayController controller = loader.getController();
                controller.setActivity(this.stage, activity, this);
                controller.lockSubject(this.subject);

                this.list.getItems().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
