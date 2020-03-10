package org.tanberg.subjecttracker.storage.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.tanberg.subjecttracker.Manager;
import org.tanberg.subjecttracker.activity.Activity;
import org.tanberg.subjecttracker.activity.assignment.Assignment;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.subject.SubjectManager;

import java.io.IOException;
import java.time.Instant;

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
        reader.beginObject();
        String type = reader.nextString();
        reader.endObject();

        SubjectManager subjectManager = Manager.getInstance().getSubjectManager();

        // Subject
        reader.beginObject();
        String subjectCode = reader.nextString();
        reader.endObject();
        Subject subject = subjectManager.getSubject(subjectCode);
        if (subject == null) {
            throw new IOException("Could not find subject \"" + subjectCode + "\"!");
        }

        // Time
        reader.beginObject();
        long epochMilli = reader.nextLong();
        reader.endObject();
        Instant time = Instant.ofEpochMilli(epochMilli);

        reader.beginObject();
        String title = reader.nextString();
        reader.endObject();

        reader.beginObject();
        String desc = reader.nextString();
        reader.endObject();

        // Use switch for future expandability
        switch (type) {
            case ASSIGNMENT_TYPE:
                reader.beginObject();
                boolean complete = reader.nextBoolean();
                reader.endObject();

                return new Assignment(subject, time, title, desc, complete);
        }

        throw new IOException("Activty \"" + type + "\" not supported!");
    }
}
