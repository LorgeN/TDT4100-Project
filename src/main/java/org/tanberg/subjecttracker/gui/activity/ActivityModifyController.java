package org.tanberg.subjecttracker.gui.activity;

import com.google.common.collect.Lists;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import org.tanberg.subjecttracker.Manager;
import org.tanberg.subjecttracker.activity.Activity;
import org.tanberg.subjecttracker.activity.ActivityManager;
import org.tanberg.subjecttracker.activity.assignment.Assignment;
import org.tanberg.subjecttracker.subject.Semester;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.util.IconUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ActivityModifyController {

    @FXML
    private Button closeButton;

    @FXML
    private ComboBox<Subject> subjectSelector;

    @FXML
    private DatePicker datePicker;

    @FXML
    private JFXTimePicker timePicker;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    private Activity handle;

    @FXML
    public void initialize() {
        this.closeButton.setGraphic(IconUtil.getIconView("close"));
        this.saveButton.setGraphic(IconUtil.getIconView("save"));
        this.deleteButton.setGraphic(IconUtil.getIconView("delete"));

        this.saveButton.setDisable(true);
        this.deleteButton.setDisable(true);
        this.datePicker.setDisable(true);
        this.timePicker.setDisable(true);

        this.subjectSelector.setConverter(new SubjectStringConverter());
        this.subjectSelector.setItems(FXCollections.observableList(Lists.newArrayList(Manager.getInstance().getSubjectManager().getSubjects())));
        this.subjectSelector.getSelectionModel().clearSelection();

        this.subjectSelector.selectionModelProperty().addListener((value, oldVal, newVal) -> {
            this.datePicker.setDisable(false);
            this.timePicker.setDisable(false);
        });


        this.datePicker.setDayCellFactory(new Callback<>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                Subject subject = subjectSelector.getValue();
                Semester semester = subject.getSemester();
                LocalDate minDate = semester.getMinDate();
                LocalDate maxDate = semester.getMaxDate();

                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(minDate)) {
                            this.setDisable(true);
                            this.setStyle("-fx-background-color: #ffc0cb;");
                        } else if (item.isAfter(maxDate)) {
                            this.setDisable(true);
                            this.setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        });

        this.datePicker.setValue(LocalDate.now());
        this.timePicker.setValue(LocalTime.now());

        this.titleField.textProperty().addListener((value, oldVal, newVal) ->
                this.saveButton.setDisable(!StringUtils.isBlank(newVal) && !StringUtils.isBlank(this.descriptionArea.getText())));
        this.descriptionArea.textProperty().addListener((value, oldVal, newVal) ->
                this.saveButton.setDisable(!StringUtils.isBlank(newVal) && !StringUtils.isBlank(this.titleField.getText())));
    }

    public void setUp(Activity handle) {
        this.handle = handle;

        this.deleteButton.setDisable(false);
    }

    @FXML
    public void deleteActivity() {
        if (this.handle == null) {
            return;
        }

        ActivityManager activityManager = Manager.getInstance().getActivityManager();
        activityManager.removeActivity(this.handle);

        // TODO: Refresh parent and close popup
    }

    @FXML
    public void saveActivity() {
        if (this.handle == null) {
            Subject subject = this.subjectSelector.getValue();

            LocalDate date = this.datePicker.getValue();
            LocalTime time = this.timePicker.getValue();
            Instant timestamp = Instant.from(LocalDateTime.of(date, time));

            String title = this.titleField.getText();
            String desc = this.descriptionArea.getText();

            // Only support assignment right now
            Assignment assignment = new Assignment(subject, timestamp, title, desc);
            ActivityManager activityManager = Manager.getInstance().getActivityManager();
            activityManager.addActivity(assignment);
            return;
        }

        // TODO: Refresh parent and close popup
    }

    private static class SubjectStringConverter extends StringConverter<Subject> {
        @Override
        public String toString(Subject subject) {
            return subject.getCode();
        }

        @Override
        public Subject fromString(String code) {
            return Manager.getInstance().getSubjectManager().getSubject(code);
        }
    }
}
