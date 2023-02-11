package gui.deck;

import gui.JPanelBackground;
import listeners.OnDeckClickListener;
import settings.enums.DeckType;
import util.SpriteLoader;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Deck extends JPanelBackground {
    private final DeckType type;
    private boolean selected;
    private Border defaultBorder;
    private OnDeckClickListener onDeckClickListener;

    public Deck(DeckType type, boolean selected) {
        this.type = type;
        this.selected = selected;

        initialize();
    }

    public Deck(DeckType type) {
        this.type = type;

        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());

        int index = Integer.parseInt(type.getValue());
        ImageIcon icon = new ImageIcon(SpriteLoader.getInstance().getCharacters().get(index));
        JLabel imageLabel = new JLabel(icon);
        JLabel deckName = new JLabel(getDeckName(type), JLabel.CENTER);
        JPanelBackground inner = new JPanelBackground();
        inner.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        inner.add(deckName);

        add(imageLabel, BorderLayout.CENTER);
        add(inner, BorderLayout.PAGE_END);

        configureDefaultBorder();

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (onDeckClickListener != null) {
                    onDeckClickListener.onClick(type);
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                // Empty
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                // Empty
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                setBorder(BorderFactory.createLineBorder(Color.black));
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                setBorder(defaultBorder);
            }
        });
    }

    private String getDeckName(DeckType type) {
        switch (type) {
            case JOEY: return "Joey";
            case KAIBA: return "Kaiba";
            case PEGASUS: return "Pegasus";
            case STAPLE: return "Outras";
            default: return "Yugi";
        }
    }

    private void configureDefaultBorder() {
        this.defaultBorder = selected ?
                BorderFactory.createLoweredBevelBorder() : BorderFactory.createLineBorder(getBackground());
        setBorder(defaultBorder);
    }

    public void setOnDeckClickListener(OnDeckClickListener onDeckClickListener) {
        this.onDeckClickListener = onDeckClickListener;
    }

    public DeckType getType() {
        return type;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        configureDefaultBorder();
    }

    public boolean isSelected() {
        return selected;
    }

}
