package org.tanberg.subjecttracker.gui.subject;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.tanberg.subjecttracker.Manager;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.subject.SubjectManager;

import java.io.IOException;

public class SubjectListController {

    @FXML
    private JFXListView<AnchorPane> list;

    @FXML
    private Button addButton;

    @FXML
    public void initialize() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        Image image = new Image(classLoader.getResourceAsStream("org/tanberg/subjecttracker/icons/plus.png"));
        this.addButton.setGraphic(new ImageView(image));

        this.list.setExpanded(true);

        SubjectManager manager = Manager.getInstance().getSubjectManager();

        for (Subject subject : manager.getSubjects()) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("subjectview.fxml"));

            try {
                AnchorPane pane = loader.load();
                SubjectViewController controller = loader.getController();
                controller.setSubject(subject);

                this.list.getItems().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void newSubject() {
        // TODO
    }
}
