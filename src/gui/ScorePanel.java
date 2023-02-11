package gui;

import util.FontHandler;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanelBackground {
    private int playerScore = 0;
    private static final String SCORE_TEMPLATE = "Pontuação: %02d";
    private final JLabel scoreText = new JLabel();

    public ScorePanel() {
        scoreText.setFont(FontHandler.getFont(24));
        scoreText.setVerticalAlignment(JLabel.CENTER);
        scoreText.setVerticalTextPosition(JLabel.CENTER);
        scoreText.setAlignmentY(CENTER_ALIGNMENT);
        scoreText.setForeground(Color.white);
        add(scoreText);
    }

    public void addScore(int score) {
        playerScore += score;
        updateScoreText();
    }

    public void clearScore() {
        playerScore = 0;
        updateScoreText();
    }

    private void updateScoreText() {
        scoreText.setText(String.format(SCORE_TEMPLATE, playerScore));
    }

    public int getPlayerScore() {
        return playerScore;
    }
}
