package org.tanberg.subjecttracker.gui.subject;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.tanberg.subjecttracker.Manager;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.subject.SubjectManager;
import org.tanberg.subjecttracker.util.IconUtil;

import java.awt.*;
import java.io.IOException;

public class SubjectListController {

    @FXML
    private JFXListView<AnchorPane> list;

    @FXML
    private Button addButton;

    private Stage stage;

    @FXML
    public void initialize() {
        this.addButton.setGraphic(IconUtil.getIconView("plus"));
        this.list.setExpanded(true);
    }

    public void rerender() {
        this.list.getItems().clear();
        this.setup(this.stage);
    }

    public void setup(Stage stage) {
        this.stage = stage;

        SubjectManager manager = Manager.getInstance().getSubjectManager();

        for (Subject subject : manager.getSubjects()) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("subjectview.fxml"));

            try {
                AnchorPane pane = loader.load();
                SubjectViewController controller = loader.getController();
                controller.setSubject(subject, stage, this);

                this.list.getItems().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void newSubject() {
        Popup popup = new Popup();

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("subjectmodify.fxml"));

        AnchorPane pane;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        SubjectModifyController controller = loader.getController();
        controller.setUp(popup, this);

        popup.getContent().add(pane);

        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        popup.setX(mouseLocation.getX());
        popup.setY(mouseLocation.getY());

        popup.setAutoHide(true);
        popup.show(this.stage);
    }
}
