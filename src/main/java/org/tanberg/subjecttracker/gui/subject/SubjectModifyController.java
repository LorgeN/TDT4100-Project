package org.tanberg.subjecttracker.gui.subject;

import com.google.common.collect.Lists;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import org.apache.commons.lang3.StringUtils;
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

    private StackPane dialogPane;
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

        this.colorPicker.setValue(subject.getColor());

        this.saveButton.setDisable(false);
        this.deleteButton.setDisable(false);
    }

    @FXML
    public void initialize() {
        this.dialogPane = new StackPane();
        this.background.getChildren().add(this.dialogPane);

        this.yearSelector.setItems(FXCollections.observableList(IntStream.rangeClosed(2010, 2030).boxed().collect(Collectors.toList())));
        this.seasonSelector.setItems(FXCollections.observableList(Lists.newArrayList(Semester.SemesterSeason.values())));

        this.yearSelector.getSelectionModel().selectFirst();
        this.seasonSelector.getSelectionModel().selectFirst();

        this.saveButton.setDisable(true);
        this.deleteButton.setDisable(true);

        this.codeField.textProperty().addListener((observableValue, oldVal, newVal) -> {
            this.saveButton.setDisable(StringUtils.isAnyBlank(newVal, this.nameField.getText()));

            if (!StringUtils.isBlank(this.nameField.getText())) {
                return;
            }

            this.nameField.setText(newVal);
        });

        this.nameField.textProperty().addListener((observableValue, oldVal, newVal) ->
                this.saveButton.setDisable(StringUtils.isAnyBlank(newVal, this.codeField.getText())));

        this.saveButton.setGraphic(IconUtil.getIconView("save"));
        this.deleteButton.setGraphic(IconUtil.getIconView("delete"));
        this.closeButton.setGraphic(IconUtil.getIconView("close"));
    }

    @FXML
    public void close() {
        this.popup.hide();
    }

    @FXML
    public void deleteSubject() {
        if (this.subject == null) {
            return;
        }

        JFXDialogLayout content = new JFXDialogLayout();
        content.setPrefWidth(250.0d);

        content.setHeading(new Text("Delete " + this.subject.getCode() + "?"));
        Text text = new Text("This will permanently delete the subject and any activities linked to it. You may not undo this action.");
        text.setWrappingWidth(250.0d);
        content.setBody(text);

        JFXButton cancelButton = new JFXButton("Cancel");
        cancelButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);");
        JFXButton okayButton = new JFXButton("Okay");
        okayButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);");

        JFXDialog dialog = new JFXDialog(this.dialogPane, content, JFXDialog.DialogTransition.CENTER);

        okayButton.setOnAction(event -> {
            dialog.close();
            this.close();

            Manager.getInstance().getSubjectManager().removeSubject(this.subject);
            this.listController.rerender();
        });

        cancelButton.setOnAction(event -> dialog.close());

        content.setActions(cancelButton, okayButton);
        dialog.show();
    }

    @FXML
    public void saveSubject() {
        String code = this.codeField.getText();
        String friendlyName = this.nameField.getText();
        Semester semester = new Semester(this.yearSelector.getSelectionModel().getSelectedItem(), this.seasonSelector.getSelectionModel().getSelectedItem());
        Color color = this.colorPicker.getValue();

        if (this.subject != null) {
            this.subject.setCode(code);
            this.subject.setFriendlyName(friendlyName);
            this.subject.setSemester(semester);
            this.subject.setColor(color);

            this.close();
            this.listController.rerender();
            return;
        }

        Subject subject = new Subject(code, friendlyName, semester, color);
        Manager.getInstance().getSubjectManager().addSubject(subject);

        this.close();
        this.listController.rerender();
    }
}
