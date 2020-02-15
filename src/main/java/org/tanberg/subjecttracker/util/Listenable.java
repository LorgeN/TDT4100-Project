package org.tanberg.subjecttracker.util;

import com.google.common.collect.Lists;

import java.util.List;

public class Listenable {

    private final List<Runnable> listeners;

    public Listenable() {
        this.listeners = Lists.newArrayList();
    }

    public void listen(Runnable listener) {
        this.listeners.add(listener);
    }

    protected void runListeners() {
        this.listeners.forEach(Runnable::run);
    }
}
