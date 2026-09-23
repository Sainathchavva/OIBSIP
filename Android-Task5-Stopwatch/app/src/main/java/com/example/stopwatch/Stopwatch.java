package com.example.stopwatch;

import android.os.SystemClock;

/**
 * Core stopwatch timer.
 *
 * Uses elapsedRealtime() for accurate elapsed-time calculation.
 */
public class Stopwatch {

    private long elapsedTime = 0L;
    private long startTime = 0L;
    private boolean running = false;

    public void start() {
        if (running) {
            return;
        }

        startTime = SystemClock.elapsedRealtime() - elapsedTime;
        running = true;
    }

    public void pause() {
        if (!running) {
            return;
        }

        elapsedTime = SystemClock.elapsedRealtime() - startTime;
        running = false;
    }

    public void reset() {
        elapsedTime = 0L;
        startTime = 0L;
        running = false;
    }

    public long getElapsedTime() {
        if (running) {
            return SystemClock.elapsedRealtime() - startTime;
        }

        return elapsedTime;
    }

    public boolean isRunning() {
        return running;
    }

    public void restoreState(long elapsedTime, boolean running) {
        this.elapsedTime = elapsedTime;

        if (running) {
            this.startTime =
                    SystemClock.elapsedRealtime() - elapsedTime;
        }

        this.running = running;
    }
}