package gui;

import javax.swing.*;
import java.awt.*;

public class JPanelBackground extends JPanel {
    private Image backgroundImage;

    public JPanelBackground() {
        setOpaque(false);
    }

    public JPanelBackground(boolean opaque) {
        setOpaque(opaque);
    }

    public JPanelBackground(Image background) {
        this.backgroundImage = background;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage == null) {
            return;
        }

        Image image = backgroundImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST);
        g.drawImage(image, 0, 0, null);
    }

    public void setBackgroundImage(Image background) {
        this.backgroundImage = background;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

}
