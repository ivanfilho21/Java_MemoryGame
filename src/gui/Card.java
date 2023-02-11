package gui;

import listeners.OnGuiClickListener;
import util.SpriteLoader;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Card extends JLabel {
    private final ImageIcon icon = new ImageIcon();
    private final int imageIndex;
    private boolean faceUp;
    private OnGuiClickListener onGuiClickListener = null;

    public Card(int imageIndex) {
        this.faceUp = false;
        this.imageIndex = imageIndex;

        changeCardIcon();
        configureListener();
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public int getCardIndex() {
        return imageIndex;
    }

    public void setOnGuiClickListener(OnGuiClickListener onGuiClickListener) {
        this.onGuiClickListener = onGuiClickListener;
    }

    public void reveal() {
        this.faceUp = true;
        changeCardIcon();
    }

    public void turnDown() {
        this.faceUp = false;
        changeCardIcon();
    }

    private void changeCardIcon() {
        int index = isFaceUp() ? imageIndex : 0;
        icon.setImage(SpriteLoader.getInstance().getSpriteSheet().get(index));
        setIcon(icon);
    }

    private void configureListener() {
        final Card card = this;

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onGuiClickListener != null && isEnabled() && !isFaceUp()) {
                    onGuiClickListener.onClick(card);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //
            }
        });
    }

}
