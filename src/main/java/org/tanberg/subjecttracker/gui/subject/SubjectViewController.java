package org.tanberg.subjecttracker.gui.subject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.tanberg.subjecttracker.subject.Subject;

import java.awt.*;

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

    public void setSubject(Subject subject) {
        this.subject = subject;

        Color color = this.subject.getColor();
        this.background.setStyle(String.format("-fx-background-color: rgb(%s, %s, %s, 0.25)", color.getRed(), color.getGreen(), color.getBlue()));

        this.title.setText(subject.getCode());
        this.name.setText(subject.getFriendlyName());

        ClassLoader classLoader = this.getClass().getClassLoader();
        Image editImage = new Image(classLoader.getResourceAsStream("org/tanberg/subjecttracker/icons/edit.png"));
        this.editButton.setGraphic(new ImageView(editImage));

        Image viewImage = new Image(classLoader.getResourceAsStream("org/tanberg/subjecttracker/icons/list.png"));
        this.viewButton.setGraphic(new ImageView(viewImage));

        Image statsImage = new Image(classLoader.getResourceAsStream("org/tanberg/subjecttracker/icons/stats.png"));
        this.statsButton.setGraphic(new ImageView(statsImage));
    }

    @FXML
    public void edit() {
        System.out.println("EDITING");
    }

    @FXML
    public void view() {
        System.out.println("VIEWING");
    }
}
