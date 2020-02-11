package org.tanberg.subjecttracker.gui.subject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.util.IconUtil;

import java.awt.*;
import java.io.IOException;

public class SubjectViewController {

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

        this.editButton.setGraphic(IconUtil.getIconView("edit"));
        this.viewButton.setGraphic(IconUtil.getIconView("list"));
        this.statsButton.setGraphic(IconUtil.getIconView("stats"));
    }

    @FXML
    public void editSubject() {
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
        controller.fromSubject(this.subject);
        controller.setUp(popup, this.listController);

        popup.getContent().add(pane);

        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        popup.setX(mouseLocation.getX());
        popup.setY(mouseLocation.getY());

        popup.setAutoHide(true);
        popup.show(this.stage);
    }

    @FXML
    public void viewActivities() {

    }

    @FXML
    public void viewStats() {

    }
}
