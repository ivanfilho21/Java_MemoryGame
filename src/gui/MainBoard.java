package gui;

import listeners.OnCardsMatchListener;
import listeners.OnGuiClickListener;
import listeners.OnMovesListener;
import settings.GameSettings;
import util.SoundHandler;
import util.SpriteLoader;
import util.TimeHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainBoard extends JPanelBackground {
    private final int numberOfCards;
    private final java.util.List<Card> cards = new ArrayList<>();
    private final List<Card> revealedCards = new ArrayList<>();
    private OnGuiClickListener onClickAnyCardListener = null;
    private OnCardsMatchListener onCardsMatchListener = null;
    private OnMovesListener onMovesListener = null;
    private int moves = 0;
    private int wrongMoves = 0;
    private boolean isAnimating = false;

    public MainBoard() {
        int cols;
        int rows = 4;

        switch (GameSettings.getDifficultyLevel()) {
            case MEDIUM:
                cols = 6;
                numberOfCards = 12;
                break;
            case HARD:
                cols = 7;
                rows = 6;
                numberOfCards = 21;
                break;
            default:
                cols = 4;
                numberOfCards = 8;
                break;
        }

        System.out.printf("Rows: %d, Cols: %d Number of Cards: %d\n", rows, cols, numberOfCards);

        GridLayout layout = new GridLayout(rows, cols);
        layout.setHgap(5);
        layout.setVgap(5);
        setLayout(layout);
    }

    private void configureBoard() {
        for (int n : drawCardIndexes()) {
            cards.add(new Card(n));
            cards.add(new Card(n));
        }

        Collections.shuffle(cards);

        for (Card card : cards) {
            card.setOnGuiClickListener(getCardClickListener());
            add(card);
        }
    }

    /**
     * Sorteia as cartas e retorna os índices sorteados.
     */
    private List<Integer> drawCardIndexes() {
        int maxPerDeck = SpriteLoader.CARDS_PER_DECK;
        List<Integer> cardIndexes = new ArrayList<>();

        for (int i = 1; i <= numberOfCards; i++) {
            int index = i;

            if (i > maxPerDeck) {
                do {
                    index = (new Random()).nextInt(maxPerDeck);
                } while (index == 0);
            }

            cardIndexes.add(index);
            System.out.print(" " + index);
        }

        System.out.println();
        Collections.shuffle(cardIndexes);

        for (int i : cardIndexes) {
            System.out.print(" " + i);
        }
        System.out.println();

        return cardIndexes;
    }

    /**
     * Executa sempre que uma carta do MainBoard for clicada.
     */
    private OnGuiClickListener getCardClickListener() {
        return component -> {
            if (isAnimating) {
                return;
            }

            SoundHandler.getInstance().playDealCardSE();
            Card card = (Card) component;

            if (onClickAnyCardListener != null) {
                onClickAnyCardListener.onClick(card);
            }

            revealCard(card);
        };
    }

    private void revealCard(Card card) {
        TimeHandler.delay(() -> {
            card.reveal();
            revealedCards.add(card);
            refresh();
            checkCards();
            isAnimating = false;
        }, 200, false);

        isAnimating = true;
    }

    private void checkCards() {
        if (revealedCards.size() < 2) {
            return;
        }

        moves++;
        if (onMovesListener != null) {
            onMovesListener.onMove(moves);
        }

        boolean cardsMatch = revealedCards.get(0).getCardIndex() == revealedCards.get(1).getCardIndex();

        if (cardsMatch) {
            System.out.println("Cards match");

            SoundHandler.getInstance().playSuccessSE();

            revealedCards.get(0).setEnabled(false);
            revealedCards.get(1).setEnabled(false);

            if (onCardsMatchListener != null) {
                boolean allCardsFinished = true;

                for (Card card : cards) {
                    if (!card.isFaceUp()) {
                        allCardsFinished = false;
                        break;
                    }
                }

                onCardsMatchListener.onMatch(allCardsFinished);
            }
        } else {
            System.out.println("Cards do not match");

            int time;
            switch (GameSettings.getAnimationSpeed()) {
                case NONE:
                case FAST:
                    time = TimeHandler.FAST_TIME;
                    break;
                case SLOW:
                    time = TimeHandler.SLOW_TIME;
                    break;
                default:
                    time = TimeHandler.NORMAL_TIME;
                    break;
            }

            for (Card card : revealedCards) {
                TimeHandler.delay(() -> {
                    card.turnDown();
                    refresh();
                }, time, false);
            }

            wrongMoves++;
        }

        revealedCards.clear();
    }

    private void refresh() {
        repaint();
        revalidate();
    }

    public void setOnClickAnyCardListener(OnGuiClickListener listener) {
        this.onClickAnyCardListener = listener;
    }

    public void setOnMoveListener(OnMovesListener listener) {
        this.onMovesListener = listener;
    }

    public void setOnCardsMatchListener(OnCardsMatchListener listener) {
        this.onCardsMatchListener = listener;
    }

    public int getMoves() {
        return moves;
    }

    public int getWrongMoves() {
        return wrongMoves;
    }

    public void resetBoard() {
        removeAll();
        moves = 0;
        wrongMoves = 0;
        cards.clear();
        revealedCards.clear();
        configureBoard();
    }

}
