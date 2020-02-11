package org.tanberg.subjecttracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SubjectTrackerApplication extends Application {

    public static void main(String[] args) {
        SubjectTrackerApplication.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("parent.fxml"));
        Parent root = loader.load();

        ParentController controller = loader.getController();
        controller.setup(primaryStage);

        primaryStage.setTitle("Subject Tracker");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
