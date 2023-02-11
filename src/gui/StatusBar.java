package gui;

import util.FontHandler;
import util.TimeHandler;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ScheduledExecutorService;

public class StatusBar extends JPanelBackground {
    private static final int MAX_VALUE = 999999;
    private int moves = 0;
    private int elapsedTime = -1;
    private final JLabel timeLabel = new JLabel();
    private final JLabel movesLabel = new JLabel();
    private ScheduledExecutorService timeService = null;

    public StatusBar() {
        timeLabel.setFont(FontHandler.getFont(16));
        timeLabel.setVerticalAlignment(JLabel.CENTER);
        timeLabel.setVerticalTextPosition(JLabel.CENTER);
        timeLabel.setAlignmentY(CENTER_ALIGNMENT);
        timeLabel.setForeground(Color.white);

        movesLabel.setFont(FontHandler.getFont(16));
        movesLabel.setForeground(Color.white);

        add(movesLabel);
        add(timeLabel);
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void startTimer() {
        if (timeService == null) {
            timeService = TimeHandler.delay(() -> {
                elapsedTime++;
                updateTimeText();

                if (elapsedTime >= MAX_VALUE) {
                    stopTimer();
                }
            }, TimeHandler.SLOW_TIME, true);
        }
    }

    public void stopTimer() {
        if (timeService != null) {
            timeService.shutdownNow();
            timeService = null;
        }
    }

    public void reset() {
        moves = 0;
        elapsedTime = -1;
        updateMovesText();
        updateTimeText();
    }

    public void setMoves(int moves) {
        this.moves = moves;
        updateMovesText();
    }

    private void updateTimeText() {
        int time = Math.max(elapsedTime, 0);
        timeLabel.setText("Tempo de jogo: " + time);
    }

    private void updateMovesText() {
        int value = Math.min(moves, MAX_VALUE);
        movesLabel.setText("Jogadas: " + value);
    }

}
