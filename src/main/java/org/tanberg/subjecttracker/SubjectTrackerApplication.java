package org.tanberg.subjecttracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SubjectTrackerApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("parent.fxml"));
        primaryStage.setTitle("Subject Tracker");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
