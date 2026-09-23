package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

/**
 * Main Activity for the Stopwatch application.
 *
 * Features:
 * - Start
 * - Pause
 * - Reset
 * - Lap recording
 * - Accurate time calculation
 * - Activity lifecycle handling
 * - Configuration change handling
 */
public class MainActivity extends AppCompatActivity {

    private static final String KEY_ELAPSED_TIME = "elapsed_time";
    private static final String KEY_IS_RUNNING = "is_running";
    private static final String KEY_LAP_COUNT = "lap_count";

    /*
     * UI refresh interval.
     * Actual timing is calculated by Stopwatch.java.
     */
    private static final long UPDATE_INTERVAL = 50L;

    private TextView timeDisplay;
    private TextView statusText;
    private TextView lapCountText;
    private TextView emptyLapText;

    private Button startButton;
    private Button pauseButton;
    private Button resetButton;
    private Button lapButton;

    private LinearLayout lapContainer;
    private ScrollView lapScrollView;

    private Stopwatch stopwatch;

    private int lapCount = 0;

    /*
     * Stores whether the stopwatch was running
     * before the Activity went into the background.
     */
    private boolean wasRunningBeforePause = false;

    /*
     * Handler attached to the main UI thread.
     */
    private final Handler handler =
            new Handler(Looper.getMainLooper());

    /*
     * Periodically updates the timer display.
     */
    private final Runnable updateRunnable = new Runnable() {

        @Override
        public void run() {

            if (stopwatch != null
                    && stopwatch.isRunning()) {

                updateTimeDisplay();

                handler.postDelayed(
                        this,
                        UPDATE_INTERVAL
                );
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initializeViews();

        stopwatch = new Stopwatch();

        /*
         * Restore previous state after configuration
         * changes such as screen rotation.
         */
        if (savedInstanceState != null) {

            restoreState(savedInstanceState);
        }

        setupListeners();

        updateUI();

        if (stopwatch.isRunning()) {

            startDisplayUpdates();
        }
    }


    /**
     * Connect XML views to Java variables.
     */
    private void initializeViews() {

        timeDisplay =
                findViewById(R.id.timeDisplay);

        statusText =
                findViewById(R.id.statusText);

        lapCountText =
                findViewById(R.id.lapCountText);

        emptyLapText =
                findViewById(R.id.emptyLapText);

        startButton =
                findViewById(R.id.startButton);

        pauseButton =
                findViewById(R.id.pauseButton);

        resetButton =
                findViewById(R.id.resetButton);

        lapButton =
                findViewById(R.id.lapButton);

        lapContainer =
                findViewById(R.id.lapContainer);

        lapScrollView =
                findViewById(R.id.lapScrollView);
    }


    /**
     * Register button click listeners.
     */
    private void setupListeners() {

        startButton.setOnClickListener(
                view -> startStopwatch()
        );

        pauseButton.setOnClickListener(
                view -> pauseStopwatch()
        );

        resetButton.setOnClickListener(
                view -> resetStopwatch()
        );

        lapButton.setOnClickListener(
                view -> recordLap()
        );
    }


    /**
     * Starts or resumes the stopwatch.
     */
    private void startStopwatch() {

        if (stopwatch.isRunning()) {
            return;
        }

        stopwatch.start();

        statusText.setText(
                R.string.status_running
        );

        updateUI();

        startDisplayUpdates();
    }


    /**
     * Pauses the stopwatch.
     */
    private void pauseStopwatch() {

        if (!stopwatch.isRunning()) {
            return;
        }

        stopwatch.pause();

        stopDisplayUpdates();

        statusText.setText(
                R.string.status_paused
        );

        updateUI();
    }


    /**
     * Resets the stopwatch and clears all laps.
     */
    private void resetStopwatch() {

        stopwatch.reset();

        stopDisplayUpdates();

        lapCount = 0;

        /*
         * Remove dynamically created lap views.
         *
         * The first child is emptyLapText, so we keep it.
         */
        if (lapContainer.getChildCount() > 1) {

            lapContainer.removeViews(
                    1,
                    lapContainer.getChildCount() - 1
            );
        }

        /*
         * Show empty-state message again.
         */
        emptyLapText.setVisibility(
                View.VISIBLE
        );

        statusText.setText(
                R.string.status_ready
        );

        updateUI();
    }


    /**
     * Records the current stopwatch time as a lap.
     */
    private void recordLap() {

        if (!stopwatch.isRunning()) {
            return;
        }

        lapCount++;

        long elapsedTime =
                stopwatch.getElapsedTime();

        TextView lapView =
                createLapView(
                        lapCount,
                        elapsedTime
                );

        /*
         * Hide empty-state message once
         * the first lap has been recorded.
         */
        emptyLapText.setVisibility(
                View.GONE
        );

        lapContainer.addView(lapView);

        updateLapCount();

        /*
         * Automatically scroll to the newest lap.
         */
        lapScrollView.post(
                () -> lapScrollView.fullScroll(
                        View.FOCUS_DOWN
                )
        );
    }


    /**
     * Creates a professional lap item.
     */
    private TextView createLapView(
            int number,
            long elapsedTime
    ) {

        TextView lapView =
                new TextView(this);

        String lapText =
                String.format(
                        Locale.getDefault(),
                        "Lap %02d                         %s",
                        number,
                        formatTime(elapsedTime)
                );

        lapView.setText(lapText);

        lapView.setTextColor(
                getColor(
                        R.color.text_primary
                )
        );

        lapView.setTextSize(16);

        /*
         * Correctly centers the lap text.
         */
        lapView.setGravity(
                Gravity.CENTER
        );

        lapView.setBackgroundResource(
                R.drawable.bg_lap_item
        );

        int padding =
                dpToPx(16);

        lapView.setPadding(
                padding,
                padding,
                padding,
                padding
        );

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        int margin =
                dpToPx(5);

        params.setMargins(
                0,
                margin,
                0,
                margin
        );

        lapView.setLayoutParams(params);

        return lapView;
    }


    /**
     * Updates the main stopwatch display.
     */
    private void updateTimeDisplay() {

        long elapsedTime =
                stopwatch.getElapsedTime();

        timeDisplay.setText(
                formatTime(elapsedTime)
        );
    }


    /**
     * Updates button states and lap counter.
     */
    private void updateUI() {

        boolean running =
                stopwatch.isRunning();

        /*
         * Start is disabled while running.
         */
        startButton.setEnabled(!running);

        /*
         * Pause is enabled only while running.
         */
        pauseButton.setEnabled(running);

        /*
         * Lap is enabled only while running.
         */
        lapButton.setEnabled(running);

        /*
         * Reset is always available.
         */
        resetButton.setEnabled(true);

        updateTimeDisplay();

        updateLapCount();
    }


    /**
     * Updates the lap counter.
     */
    private void updateLapCount() {

        if (lapCount == 0) {

            lapCountText.setText(
                    R.string.lap_count_zero
            );

            return;
        }

        lapCountText.setText(
                getString(
                        R.string.lap_count,
                        lapCount
                )
        );
    }


    /**
     * Starts Handler-based UI updates.
     */
    private void startDisplayUpdates() {

        handler.removeCallbacks(
                updateRunnable
        );

        handler.post(
                updateRunnable
        );
    }


    /**
     * Stops Handler-based UI updates.
     */
    private void stopDisplayUpdates() {

        handler.removeCallbacks(
                updateRunnable
        );
    }


    /**
     * Converts milliseconds into HH:MM:SS.
     */
    private String formatTime(
            long milliseconds
    ) {

        long totalSeconds =
                milliseconds / 1000;

        long hours =
                totalSeconds / 3600;

        long minutes =
                (totalSeconds % 3600) / 60;

        long seconds =
                totalSeconds % 60;

        return String.format(
                Locale.getDefault(),
                "%02d:%02d:%02d",
                hours,
                minutes,
                seconds
        );
    }


    /**
     * Converts dp to pixels.
     */
    private int dpToPx(int dp) {

        return Math.round(
                dp * getResources()
                        .getDisplayMetrics()
                        .density
        );
    }


    /**
     * Saves stopwatch state during Activity recreation.
     */
    @Override
    protected void onSaveInstanceState(
            Bundle outState
    ) {

        super.onSaveInstanceState(
                outState
        );

        outState.putLong(
                KEY_ELAPSED_TIME,
                stopwatch.getElapsedTime()
        );

        outState.putBoolean(
                KEY_IS_RUNNING,
                stopwatch.isRunning()
        );

        outState.putInt(
                KEY_LAP_COUNT,
                lapCount
        );
    }


    /**
     * Restores stopwatch state.
     */
    private void restoreState(
            Bundle savedInstanceState
    ) {

        long elapsedTime =
                savedInstanceState.getLong(
                        KEY_ELAPSED_TIME,
                        0L
                );

        boolean isRunning =
                savedInstanceState.getBoolean(
                        KEY_IS_RUNNING,
                        false
                );

        lapCount =
                savedInstanceState.getInt(
                        KEY_LAP_COUNT,
                        0
                );

        stopwatch.restoreState(
                elapsedTime,
                isRunning
        );

        /*
         * If there are saved laps, hide the empty-state
         * message. The actual lap views aren't restored
         * because only the lap count is persisted.
         */
        if (lapCount > 0) {

            emptyLapText.setVisibility(
                    View.GONE
            );
        }
    }


    /**
     * Called when Activity goes into the background.
     */
    @Override
    protected void onPause() {

        super.onPause();

        wasRunningBeforePause =
                stopwatch.isRunning();

        if (wasRunningBeforePause) {

            stopwatch.pause();
        }

        stopDisplayUpdates();
    }


    /**
     * Called when Activity becomes visible again.
     */
    @Override
    protected void onResume() {

        super.onResume();

        if (wasRunningBeforePause) {

            stopwatch.start();

            statusText.setText(
                    R.string.status_running
            );

            startDisplayUpdates();
        }

        updateUI();
    }


    /**
     * Remove Handler callbacks when Activity is destroyed.
     */
    @Override
    protected void onDestroy() {

        stopDisplayUpdates();

        super.onDestroy();
    }
}