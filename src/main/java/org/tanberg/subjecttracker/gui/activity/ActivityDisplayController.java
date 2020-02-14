package org.tanberg.subjecttracker.gui.activity;

import com.jfoenix.controls.JFXTooltip;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.tanberg.subjecttracker.activity.Activity;
import org.tanberg.subjecttracker.activity.assignment.Assignment;
import org.tanberg.subjecttracker.util.IconUtil;

import java.awt.*;
import java.io.IOException;

public class ActivityDisplayController {

    @FXML
    private Text title;

    @FXML
    private Button editButton;

    @FXML
    private ToggleButton completeButton;

    private Activity activity;
    private Stage stage;

    @FXML
    public void initialize() {
        this.editButton.setGraphic(IconUtil.getIconView("edit"));
        this.completeButton.selectedProperty().addListener((value, oldVal, newVal) -> ((Assignment) this.activity).setComplete(newVal));
    }

    public void setActivity(Stage stage, Activity activity) {
        this.stage = stage;
        this.activity = activity;

        this.title.setText(activity.getTitle());

        Tooltip tooltip = new JFXTooltip();
        tooltip.setWrapText(true);
        tooltip.setWidth(300.0d);
        tooltip.setTextAlignment(TextAlignment.LEFT);

        
    }

    @FXML
    public void editActivity() {
        Popup popup = new Popup();

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("activitymodify.fxml"));

        AnchorPane pane;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ActivityModifyController controller = loader.getController();
        controller.setUp(this.activity);

        popup.getContent().add(pane);

        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        popup.setX(mouseLocation.getX());
        popup.setY(mouseLocation.getY());

        popup.setAutoHide(true);
        popup.show(this.stage);
    }
}
