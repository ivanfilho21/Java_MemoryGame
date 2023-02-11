package gui.deck;

import gui.JPanelBackground;
import listeners.OnDeckClickListener;
import settings.GameSettings;
import settings.enums.DeckType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DeckSelector extends JDialog implements OnDeckClickListener {
    private DeckType selectedDeck;
    private final List<Deck> decks = new ArrayList<>();

    public DeckSelector(JFrame parent){
        super(parent, true);
        selectedDeck = GameSettings.getDeckType();

        createDecks();

        setTitle("Selecione o baralho");
        setSize(360, 360);
        setResizable(false);
        setLocationRelativeTo(parent);

        JPanelBackground mainPanel = new JPanelBackground();
        mainPanel.setLayout(null);

        JPanelBackground deckPanel = new JPanelBackground();
        GridLayout gridLayout = new GridLayout(2, 3);
        deckPanel.setLayout(gridLayout);

        for (Deck deck : decks) {
            deck.setSelected(GameSettings.getDeckType() == deck.getType());
            deck.setOnDeckClickListener(this);
            deckPanel.add(deck);
        }

        deckPanel.setSize(getWidth(), 250);
        deckPanel.setLocation(0, 0);

        JButton btnOk = new JButton("OK");
        btnOk.setSize(70, 30);
        int btnX = getWidth() / 2 - btnOk.getWidth() / 2;
        int btnY = deckPanel.getY() + deckPanel.getHeight() + 30;
        btnOk.setLocation(btnX, btnY);

        btnOk.addActionListener(e -> {
            GameSettings.setDeckType(selectedDeck);
            dispose();
        });

        mainPanel.add(deckPanel);
        mainPanel.add(btnOk);

        add(mainPanel);

        setVisible(true);
    }

    private void createDecks() {
        decks.add(new Deck(DeckType.YUGI));
        decks.add(new Deck(DeckType.KAIBA));
        decks.add(new Deck(DeckType.JOEY));
        decks.add(new Deck(DeckType.PEGASUS));
        decks.add(new Deck(DeckType.STAPLE));
    }

    @Override
    public void onClick(DeckType deckType) {
        selectedDeck = deckType;

        for (Deck deck : decks) {
            deck.setSelected(deck.getType() == deckType);
        }
    }
}
