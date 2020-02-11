package org.tanberg.subjecttracker.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class IconUtil {

    public static ImageView getIconView(String name) {
        return new ImageView(getIcon(name));
    }

    public static Image getIcon(String name) {
        ClassLoader classLoader = IconUtil.class.getClassLoader();
        InputStream stream = classLoader.getResourceAsStream("org/tanberg/subjecttracker/icons/" + name + ".png");
        if (stream == null) {
            throw new IllegalArgumentException("Unknown icon \"" + name + "\"!");
        }

        return new Image(stream);
    }
}
