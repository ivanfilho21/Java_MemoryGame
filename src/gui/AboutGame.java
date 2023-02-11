package gui;

import util.FontHandler;
import util.SpriteLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutGame extends JDialog {

    public AboutGame(JFrame parent) {
        super(parent, true);

        setTitle("Sobre o Jogo da Memória");
        setSize(540, 280);
        setResizable(false);
        setLocationRelativeTo(parent);

        JPanelBackground mainPanel = new JPanelBackground();
        mainPanel.setLayout(new BorderLayout());

        Image img = SpriteLoader.getInstance().getImages().get(1).getScaledInstance(100, 90, Image.SCALE_FAST);
        JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        imgPanel.add(new JLabel(new ImageIcon(img)));
        mainPanel.add(imgPanel, BorderLayout.BEFORE_LINE_BEGINS);

        JPanelBackground textsPanel = new JPanelBackground();
        JLabel title = new JLabel("Jogo da Memória");
        title.setFont(FontHandler.getFont(20, Font.BOLD));

        textsPanel.setLayout(new GridLayout(9, 1));
        textsPanel.add(title);

        JLabel version = new JLabel("BETA 0.0.1");
        version.setFont(FontHandler.getFont(12));

        textsPanel.add(version);
        textsPanel.add(new JLabel());
        textsPanel.add(new JLabel("Este é um jogo sem fins lucrativos feito por um fã."));
        textsPanel.add(new JLabel("O jogo utiliza livremente imagens de cartas de Yu-Gi-Oh!"));
        textsPanel.add(new JLabel());
        textsPanel.add(new JLabel("Desenvolvedor: Ivan Filho"));

        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        linkPanel.add(new JLabel("GitHub: "));

        JLabel link = new JLabel("https://www.github.com/ivanfilho21");
        link.setForeground(Color.blue.darker());
        link.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.github.com/ivanfilho21"));
                } catch (IOException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        linkPanel.add(link);
        textsPanel.add(linkPanel);

        mainPanel.add(textsPanel, BorderLayout.CENTER);

        JPanelBackground bottomPanel = new JPanelBackground();
        JLabel footer = new JLabel("Todos os direitos reservados aos seus respectivos donos.");
        footer.setFont(FontHandler.getFont(12));
        bottomPanel.add(footer);

        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);

        add(mainPanel);
        setVisible(true);
    }

}
