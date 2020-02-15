package org.tanberg.subjecttracker.gui.activity;

import com.google.common.collect.Lists;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import org.tanberg.subjecttracker.Manager;
import org.tanberg.subjecttracker.activity.Activity;
import org.tanberg.subjecttracker.activity.ActivityManager;
import org.tanberg.subjecttracker.activity.assignment.Assignment;
import org.tanberg.subjecttracker.gui.node.DateTimePicker;
import org.tanberg.subjecttracker.subject.Semester;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.util.IconUtil;

import java.time.*;
import java.util.function.BiConsumer;

public class ActivityModifyController {

    @FXML
    private Button closeButton;

    @FXML
    private ComboBox<Subject> subjectSelector;

    @FXML
    private GridPane pane;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    private DateTimePicker datePicker;
    private Popup popup;
    private Activity handle;
    private BiConsumer<Activity, Boolean> updateFunction;

    @FXML
    public void initialize() {
        this.closeButton.setGraphic(IconUtil.getIconView("close"));
        this.saveButton.setGraphic(IconUtil.getIconView("save"));
        this.deleteButton.setGraphic(IconUtil.getIconView("delete"));

        this.datePicker = new DateTimePicker();

        GridPane.setMargin(this.datePicker, new Insets(0, 25, 0, 25));
        this.pane.add(this.datePicker, 1, 1);

        this.saveButton.setDisable(true);
        this.deleteButton.setDisable(true);

        this.datePicker.setDateTimeValue(LocalDateTime.now());
        this.datePicker.setDisable(true);

        this.subjectSelector.setConverter(new SubjectStringConverter());
        this.subjectSelector.setItems(FXCollections.observableList(Lists.newArrayList(Manager.getInstance().getSubjectManager().getSubjects())));
        this.subjectSelector.getSelectionModel().clearSelection();

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

                        if (!item.isBefore(minDate) && !item.isAfter(maxDate)) {
                            return;
                        }

                        this.setDisable(true);
                        this.setStyle("-fx-background-color: #ffc0cb;");
                    }
                };
            }
        });

        this.titleField.textProperty().addListener((value, oldVal, newVal) ->
                this.saveButton.setDisable(StringUtils.isBlank(newVal)
                        || StringUtils.isBlank(this.descriptionArea.getText())
                        || this.datePicker.getDateTimeValue() == null
                        || this.subjectSelector.getSelectionModel().isEmpty()));

        this.descriptionArea.textProperty().addListener((value, oldVal, newVal) ->
                this.saveButton.setDisable(StringUtils.isBlank(newVal)
                        || StringUtils.isBlank(this.titleField.getText())
                        || this.datePicker.getDateTimeValue() == null
                        || this.subjectSelector.getSelectionModel().isEmpty()));

        this.datePicker.dateTimeValueProperty().addListener((value, oldVal, newVal) ->
                this.saveButton.setDisable(StringUtils.isBlank(this.titleField.getText())
                        || StringUtils.isBlank(this.titleField.getText())
                        || this.subjectSelector.getSelectionModel().isEmpty()));

        this.subjectSelector.valueProperty().addListener((value, oldVal, newVal) ->
                this.saveButton.setDisable(StringUtils.isBlank(this.titleField.getText())
                        || StringUtils.isBlank(this.titleField.getText())
                        || this.datePicker.getDateTimeValue() == null));
    }

    public void setUp(Popup popup, BiConsumer<Activity, Boolean> updateFunction) {
        this.popup = popup;
        this.updateFunction = updateFunction;
    }

    public void setDate(LocalDateTime time) {
        this.datePicker.setDateTimeValue(time);
    }

    public void fromActivity(Activity handle) {
        this.handle = handle;

        this.subjectSelector.getSelectionModel().select(handle.getSubject());
        this.titleField.setText(handle.getTitle());
        this.descriptionArea.setText(handle.getDescription());
        this.datePicker.setDateTimeValue(LocalDateTime.ofInstant(handle.getDate(), ZoneId.systemDefault()));

        this.deleteButton.setDisable(false);
        this.datePicker.setDisable(false);
        this.subjectSelector.setDisable(false);
    }

    public void lockSubject(Subject subject) {
        if (subject == null) {
            return;
        }

        this.subjectSelector.getSelectionModel().select(subject);
        this.subjectSelector.setDisable(true);
        this.datePicker.setDisable(false);
    }

    @FXML
    public void onSelectSubject() {
        this.datePicker.setDisable(false);
    }

    @FXML
    public void close() {
        this.popup.hide();
        this.updateFunction.accept(null, false);
    }

    @FXML
    public void deleteActivity() {
        if (this.handle == null) {
            return;
        }

        ActivityManager activityManager = Manager.getInstance().getActivityManager();
        activityManager.removeActivity(this.handle);

        this.popup.hide();
        this.updateFunction.accept(this.handle, true);
    }

    @FXML
    public void saveActivity() {
        Subject subject = this.subjectSelector.getValue();

        Instant timestamp = Instant.from(ZonedDateTime.of(this.datePicker.getDateTimeValue(), ZoneId.systemDefault()));

        String title = this.titleField.getText();
        String desc = this.descriptionArea.getText();

        if (this.handle != null) {
            this.handle.setSubject(subject);
            this.handle.setDate(timestamp);
            this.handle.setTitle(title);
            this.handle.setDescription(desc);

            this.popup.hide();
            this.updateFunction.accept(null, false);
            return;
        }

        // Only support assignment right now
        Assignment assignment = new Assignment(subject, timestamp, title, desc);
        ActivityManager activityManager = Manager.getInstance().getActivityManager();
        activityManager.addActivity(assignment);

        this.popup.hide();
        this.updateFunction.accept(assignment, false);
    }

    private static class SubjectStringConverter extends StringConverter<Subject> {
        @Override
        public String toString(Subject subject) {
            return subject == null ? "" : subject.getCode();
        }

        @Override
        public Subject fromString(String code) {
            return code == null ? null : Manager.getInstance().getSubjectManager().getSubject(code);
        }
    }
}
