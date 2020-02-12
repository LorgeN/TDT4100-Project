package org.tanberg.subjecttracker.gui.activity;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.tanberg.subjecttracker.util.IconUtil;

public class SubjectActivityListController {

    @FXML
    private Button closeButton;

    @FXML
    private Button createNewButton;

    @FXML
    private Text header;

    @FXML
    private ListView<AnchorPane> activityList;

    @FXML
    public void initialize() {
        this.closeButton.setGraphic(IconUtil.getIconView("close"));
    }
}
