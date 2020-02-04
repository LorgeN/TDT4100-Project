package org.tanberg.subjecttracker.activity.goal;

import org.tanberg.subjecttracker.activity.AbstractActivity;
import org.tanberg.subjecttracker.subject.Subject;

import java.time.Instant;
import java.util.Arrays;

public class Goal extends AbstractActivity {

    private PartialGoal[] partialGoals;

    public Goal(Subject subject, Instant date, String title, String description, PartialGoal... partialGoals) {
        super(subject, date, title, description);
        this.partialGoals = partialGoals;
    }

    public PartialGoal[] getPartialGoals() {
        return partialGoals;
    }

    public void removeGoal(int index) {
        if (index < 0 || index >= this.partialGoals.length) {
            throw new IndexOutOfBoundsException(index);
        }

        PartialGoal[] current = this.partialGoals;
        this.partialGoals = new PartialGoal[this.partialGoals.length - 1];
        System.arraycopy(current, 0, this.partialGoals, 0, index);
        System.arraycopy(current, index, this.partialGoals, index, this.partialGoals.length);
    }

    public void addGoal(PartialGoal goal) {
        this.partialGoals = Arrays.copyOf(this.partialGoals, this.partialGoals.length + 1);
        this.partialGoals[this.partialGoals.length - 1] = goal;
    }

    @Override
    public double getCompletionState() {
        return (double) Arrays.stream(this.partialGoals)
                .filter(PartialGoal::isComplete)
                .count() / (double) this.partialGoals.length;
    }
}
