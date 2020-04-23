package org.tanberg.test;

import com.google.common.collect.Lists;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import org.tanberg.subjecttracker.storage.StorageManager;
import org.tanberg.subjecttracker.subject.Semester;
import org.tanberg.subjecttracker.subject.Subject;
import org.tanberg.subjecttracker.subject.SubjectManager;
import org.tanberg.test.storage.DummyStorageManager;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class SubjectManagerTest {

    private SubjectManager subjectManager;
    private List<Subject> subjects;

    @Before
    public void setUp() throws Exception {
        StorageManager manager = new DummyStorageManager();
        this.subjectManager = new SubjectManager(manager);

        this.subjects = Lists.newArrayList();

        Semester semester = new Semester(2020, Semester.SemesterSeason.SPRING);
        this.subjects.add(new Subject("EKS0001", "Eksempel 1", semester, Color.BLUE));
        this.subjects.add(new Subject("EKS0002", "Eksempel 2", semester, Color.GREEN));
        this.subjects.add(new Subject("EKS1003", "Eksempel 3", semester, Color.RED));
        this.subjects.add(new Subject("EKS1004", "Eksempel 4", semester, Color.PURPLE));

        for (Subject subject : this.subjects) {
            this.subjectManager.addSubject(subject);
        }
    }

    @Test
    public void getSubjects() {
        assertThat(this.subjectManager.getSubjects()).containsExactly(this.subjects.toArray());
    }

    @Test
    public void search() {
        assertThat(this.subjectManager.search("EkS")).containsExactly(this.subjects.toArray());
        assertThat(this.subjectManager.search("eksempel")).containsExactly(this.subjects.toArray());
        assertThat(this.subjectManager.search("0001")).containsExactly(this.subjects.get(0));
        assertThat(this.subjectManager.search("eks0001")).containsExactly(this.subjects.get(0));
        assertThat(this.subjectManager.search("EksEMpel 2")).containsExactly(this.subjects.get(1));
        assertThat(this.subjectManager.search("2")).containsExactly(this.subjects.get(1));
        assertThat(this.subjectManager.search("100")).containsExactly(this.subjects.get(2), this.subjects.get(3));
    }

    @Test
    public void addSubject() {
        Semester semester = new Semester(2020, Semester.SemesterSeason.SPRING);
        Subject eks2001 = new Subject("EKS2001", "Eksempel 2.1", semester, Color.BLUE);

        this.subjectManager.addSubject(eks2001);
        this.subjects.add(eks2001);

        assertThat(this.subjectManager.getSubjects()).containsExactly(this.subjects.toArray());
    }

    @Test
    public void removeSubject() {
        Subject subject = this.subjects.get(2);

        this.subjects.remove(subject);
        this.subjectManager.removeSubject(subject);

        assertThat(this.subjectManager.getSubjects()).containsExactly(this.subjects.toArray());
    }
}