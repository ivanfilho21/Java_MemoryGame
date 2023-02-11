package gui;

import util.SoundHandler;
import util.SpriteLoader;
import util.TimeHandler;

import javax.swing.*;

public class MemoryGame extends JPanelBackground {
    private static final int SCORE_AMOUNT = 10;
    private final ScorePanel scorePanel = new ScorePanel();
    private final StatusBar statusBar = new StatusBar();
    private final MainBoard mainBoard = new MainBoard();

    public MemoryGame(JFrame frame) {
        super(SpriteLoader.getInstance().getImages().get(0));

        int width = frame.getWidth();
        int height = frame.getHeight() - frame.getJMenuBar().getPreferredSize().height;
        setLayout(null);

        mainBoard.setOnClickAnyCardListener(card -> statusBar.startTimer());
        mainBoard.setOnMoveListener(statusBar::setMoves);
        mainBoard.setOnCardsMatchListener(finishedCards -> {
            scorePanel.addScore(SCORE_AMOUNT);

            if (finishedCards) {
                showWinDialog();
                newGame();
            }
        });

        scorePanel.setLocation(0, 0);
        scorePanel.setSize(width, 40);

        int boardWidth = (width - width / 10) + 10;
        mainBoard.setSize(boardWidth, height - (scorePanel.getHeight() + 40 + 30));
        mainBoard.setLocation((width / 2 - boardWidth / 2), scorePanel.getY() + scorePanel.getHeight());

        statusBar.setSize(width, 30);
        statusBar.setLocation(0, mainBoard.getY() + mainBoard.getHeight());

        add(scorePanel);
        add(mainBoard);
        add(statusBar);
    }

    private void showWinDialog() {
        System.out.println("You win");
        statusBar.stopTimer();

        TimeHandler.delay(() ->
                SoundHandler.getInstance().playWinGameSE(), TimeHandler.NORMAL_TIME, false);

        String message = "Muito bem, você venceu o jogo!" +
                "\n\nPontuação: " + scorePanel.getPlayerScore() + "." +
                "\n\nMovimentos errados: " + mainBoard.getWrongMoves() + "." +
                "\n\nSeu tempo foi de " + statusBar.getElapsedTime() + " segundos." +
                "\n\n";

        JOptionPane.showMessageDialog(
                this,
                message,
                "Você venceu.",
                JOptionPane.INFORMATION_MESSAGE
        );
        newGame();
    }

    public void newGame() {
        scorePanel.clearScore();
        statusBar.reset();
        mainBoard.resetBoard();
        repaint();
        revalidate();
    }

}
