package org.tanberg.subjecttracker.storage.gson;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.tanberg.subjecttracker.Manager;
import org.tanberg.subjecttracker.activity.Activity;
import org.tanberg.subjecttracker.activity.assignment.Assignment;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.subject.SubjectManager;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

public class ActivityTypeAdapter extends TypeAdapter<Activity> {

    private static final String ASSIGNMENT_TYPE = "assignment";

    @Override
    public void write(JsonWriter writer, Activity activity) throws IOException {
        String type = null;
        if (activity instanceof Assignment) {
            type = ASSIGNMENT_TYPE;
        }

        if (type == null) {
            throw new IOException("Activty \"" + activity.getClass().getName() + "\" not supported!");
        }

        writer.beginObject().name("type").value(ASSIGNMENT_TYPE).endObject()
                .beginObject().name("subject").value(activity.getSubject().getCode()).endObject()
                .beginObject().name("date").value(activity.getDate().toEpochMilli()).endObject()
                .beginObject().name("title").value(activity.getTitle()).endObject()
                .beginObject().name("description").value(activity.getDescription()).endObject();

        // Not needed for now but keep it here for future expandability
        switch (type) {
            case ASSIGNMENT_TYPE:
                writer.beginObject().name("complete").value(activity.isComplete()).endObject();
        }
    }

    @Override
    public Activity read(JsonReader reader) throws IOException {
        SubjectManager subjectManager = Manager.getInstance().getSubjectManager();
        String type = null;
        Subject subject = null;
        Instant time = null;
        String title = null;
        String desc = null;

        Map<String, Object> elements = Maps.newHashMap();

        while (reader.hasNext()) {
            reader.beginObject();
            String name = reader.nextName().toLowerCase();

            switch (name) {
                case "type":
                    type = reader.nextString();
                    break;
                case "subject":
                    String subjectCode = reader.nextString();
                    subject = subjectManager.getSubject(subjectCode);
                    if (subject == null) {
                        throw new IOException("Invalid subject \"" + subjectCode + "\"!");
                    }

                    break;
                case "date":
                    time = Instant.ofEpochMilli(reader.nextLong());
                    break;
                case "title":
                    title = reader.nextString();
                    break;
                case "description":
                    desc = reader.nextString();
                    break;
                default:
                    JsonToken peek = reader.peek();
                    if (peek == JsonToken.BOOLEAN) {
                        elements.put(name, reader.nextBoolean());
                    } else {
                        throw new IllegalStateException("Unexpected value: " + peek);
                    }
                    break;
            }

            reader.endObject();
        }

        if (type == null || subject == null || time == null || title == null || desc == null) {
            throw new IOException("Malformed activity");
        }

        switch (type) {
            case ASSIGNMENT_TYPE:
                boolean complete = (boolean) elements.get("complete");
                return new Assignment(subject, time, title, desc, complete);
        }

        throw new IOException("Invalid type \"" + type + "\"");
    }
}
