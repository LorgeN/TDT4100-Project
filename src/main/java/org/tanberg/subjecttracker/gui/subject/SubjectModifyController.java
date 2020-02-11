package org.tanberg.subjecttracker.gui.subject;

import com.google.common.collect.Lists;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import org.tanberg.subjecttracker.Manager;
import org.tanberg.subjecttracker.subject.Semester;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.util.IconUtil;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SubjectModifyController {

    @FXML
    private AnchorPane background;

    @FXML
    private Button closeButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField codeField;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<Integer> yearSelector;

    @FXML
    private ComboBox<Semester.SemesterSeason> seasonSelector;

    @FXML
    private ColorPicker colorPicker;

    private Popup popup;
    private SubjectListController listController;
    private Subject subject;

    public void setUp(Popup popup, SubjectListController listController) {
        this.popup = popup;
        this.listController = listController;
    }

    public void fromSubject(Subject subject) {
        this.subject = subject;

        this.codeField.setText(subject.getCode());
        this.nameField.setText(subject.getFriendlyName());
        this.yearSelector.getSelectionModel().select(Integer.valueOf(subject.getSemester().getYear()));
        this.seasonSelector.getSelectionModel().select(subject.getSemester().getSeason());

        java.awt.Color subjectColor = subject.getColor();
        this.colorPicker.setValue(Color.rgb(subjectColor.getRed(), subjectColor.getBlue(), subjectColor.getGreen()));

        this.saveButton.setDisable(false);
        this.deleteButton.setDisable(false);
    }

    @FXML
    public void initialize() {
        this.background.setStyle(
                "-fx-background-color: #fafffb; " +
                        "-fx-background-insets: 10; " +
                        "-fx-effect: dropshadow(three-pass-box, #000000, 5, 0, 0, 0);");

        this.yearSelector.setItems(FXCollections.observableList(IntStream.rangeClosed(2010, 2030).boxed().collect(Collectors.toList())));
        this.seasonSelector.setItems(FXCollections.observableList(Lists.newArrayList(Semester.SemesterSeason.values())));

        this.yearSelector.getSelectionModel().selectFirst();
        this.seasonSelector.getSelectionModel().selectFirst();

        this.saveButton.setDisable(true);
        this.deleteButton.setDisable(true);

        this.saveButton.setGraphic(IconUtil.getIconView("save"));
        this.deleteButton.setGraphic(IconUtil.getIconView("delete"));
        this.closeButton.setGraphic(IconUtil.getIconView("close"));
    }

    @FXML
    public void close() {
        this.popup.hide();
    }

    @FXML
    public void onCodeWrite() {

    }

    @FXML
    public void onNameWrite() {

    }

    @FXML
    public void onYearSelect() {

    }

    @FXML
    public void onSeasonSelect() {

    }

    @FXML
    public void deleteSubject() {
        if (this.subject == null) {
            return;
        }

        this.close();
        Manager.getInstance().getSubjectManager().removeSubject(this.subject);
        this.listController.rerender();
    }

    @FXML
    public void saveSubject() {

    }

    public void verifyValid() {

    }
}
