package org.tanberg.subjecttracker.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.function.BiConsumer;

public class PopupUtil {

    public static <T> void createPopup(String file, Stage stage, BiConsumer<Popup, T> controllerConsumer) {
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        createPopup(file, mouseLocation.getX(), mouseLocation.getY(), stage, controllerConsumer);
    }

    public static <T> void createPopup(String file, double x, double y, Stage stage, BiConsumer<Popup, T> controllerConsumer) {
        Popup popup = new Popup();

        ClassLoader classLoader = PopupUtil.class.getClassLoader();
        FXMLLoader loader = new FXMLLoader(classLoader.getResource(file));

        AnchorPane pane;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        pane.setStyle("-fx-background-color: #fafffb; " +
                "-fx-background-insets: 10; " +
                "-fx-effect: dropshadow(three-pass-box, #000000, 5, 0, 0, 0);");

        T controller = loader.getController();
        controllerConsumer.accept(popup, controller);

        popup.getContent().add(pane);

        popup.setX(x);
        popup.setY(y);

        popup.setAutoHide(true);
        popup.show(stage);
    }
}
