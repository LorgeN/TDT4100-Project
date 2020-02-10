package org.tanberg.subjecttracker.gui.subject;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.tanberg.subjecttracker.subject.Subject;

import java.awt.*;

public class SubjectViewController {

    @FXML
    private Pane background;

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
