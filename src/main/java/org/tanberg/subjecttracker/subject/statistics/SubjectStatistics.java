package org.tanberg.subjecttracker.subject.statistics;

import org.tanberg.subjecttracker.Manager;
import org.tanberg.subjecttracker.activity.Activity;
import org.tanberg.subjecttracker.activity.ActivityManager;
import org.tanberg.subjecttracker.activity.assignment.Assignment;
import org.tanberg.subjecttracker.activity.goal.Goal;
import org.tanberg.subjecttracker.activity.goal.PartialGoal;
import org.tanberg.subjecttracker.subject.Subject;

import java.util.Arrays;
import java.util.stream.Stream;

public class SubjectStatistics {

    private final Subject subject;

    public SubjectStatistics(Subject subject) {
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }

    public int getTotalActivities() {
        return (int) this.getSelfActivityStream().count();
    }

    public int getCompletedAssignments() {
        return (int) this.getSelfActivityStream()
                .filter(activity -> activity instanceof Assignment)
                .filter(Activity::isComplete)
                .count();
    }

    public int getTotalAssignments() {
        return (int) this.getSelfActivityStream()
                .filter(activity -> activity instanceof Assignment)
                .count();
    }

    public int getCompletedGoals() {
        return (int) this.getSelfActivityStream()
                .filter(activity -> activity instanceof Goal)
                .flatMap(activity -> Arrays.stream(((Goal) activity).getPartialGoals()))
                .filter(PartialGoal::isComplete)
                .count();
    }

    public int getTotalGoals() {
        return this.getSelfActivityStream()
                .filter(activity -> activity instanceof Goal)
                .mapToInt(activity -> ((Goal) activity).getPartialGoals().length)
                .sum();
    }

    private ActivityManager getActivityManager() {
        return Manager.getInstance().getActivityManager();
    }

    private Stream<Activity> getSelfActivityStream() {
        return this.getActivityManager().getActivities().stream()
                .filter(activity -> activity.getSubject().equals(this.subject));
    }
}
