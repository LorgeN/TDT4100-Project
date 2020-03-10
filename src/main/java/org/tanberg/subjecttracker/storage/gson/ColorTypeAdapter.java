package org.tanberg.subjecttracker.storage.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ColorTypeAdapter extends TypeAdapter<Color> {

    @Override
    public void write(JsonWriter jsonWriter, Color color) throws IOException {
        jsonWriter.beginArray()
                .value(color.getRed())
                .value(color.getGreen())
                .value(color.getBlue())
                .endArray();
    }

    @Override
    public Color read(JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();
        double red = jsonReader.nextDouble();
        double green = jsonReader.nextDouble();
        double blue = jsonReader.nextDouble();
        return Color.color(red, green, blue);
    }
}
