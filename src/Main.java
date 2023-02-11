import gui.AboutGame;
import gui.deck.DeckSelector;
import gui.MemoryGame;
import settings.GameSettings;
import settings.enums.AnimationSpeed;
import settings.enums.DifficultyLevel;
import util.SoundHandler;
import util.SpriteLoader;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
public class Main {
    private static MemoryGame game;
    private static final JFrame frame = new JFrame("Jogo da Memória");

    public static void main(String[] args) {
        initFrame();

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Jogo");
        JMenu difficultyMenu = new JMenu("Dificuldade");
        JMenu settingsMenu = new JMenu("Configurações");
        JMenu helpMenu = new JMenu("Ajuda");

        // Game menu items
        JMenuItem newGameItem = new JMenuItem("Novo Jogo");
        JMenuItem deckItem = new JMenuItem("Selecionar Baralho...");
        JMenuItem quitItem = new JMenuItem("Sair");

        newGameItem.addActionListener(e -> startNewGame());
        deckItem.addActionListener(e -> showDeckSelector());
        quitItem.addActionListener(e -> System.exit(1));

        gameMenu.add(newGameItem);
        gameMenu.add(deckItem);
        gameMenu.addSeparator();
        gameMenu.add(quitItem);

        // Difficulty Level menu items
        JRadioButtonMenuItem easyItem = new JRadioButtonMenuItem("Fácil");
        JRadioButtonMenuItem mediumItem = new JRadioButtonMenuItem("Normal");
        JRadioButtonMenuItem hardItem = new JRadioButtonMenuItem("Difícil");

        ButtonGroup difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyItem);
        difficultyGroup.add(mediumItem);
        difficultyGroup.add(hardItem);

        difficultyMenu.add(easyItem);
        difficultyMenu.add(mediumItem);
        difficultyMenu.add(hardItem);

        easyItem.addActionListener(e -> {
            GameSettings.setDifficultyLevel(DifficultyLevel.EASY);
            startNewGame();
        });
        mediumItem.addActionListener(e -> {
            GameSettings.setDifficultyLevel(DifficultyLevel.MEDIUM);
            startNewGame();
        });
        hardItem.addActionListener(e -> {
            GameSettings.setDifficultyLevel(DifficultyLevel.HARD);
            startNewGame();
        });

        switch (GameSettings.getDifficultyLevel()) {
            case EASY:
                easyItem.setSelected(true);
                break;
            case MEDIUM:
                mediumItem.setSelected(true);
                break;
            case HARD:
                hardItem.setSelected(true);
                break;
        }

        // Settings menu items
        JMenu animationSpeedSubmenu = new JMenu("Animações");
        //JRadioButtonMenuItem noAnimationSubItem = new JRadioButtonMenuItem("Desligado");
        JRadioButtonMenuItem slowSpeedSubItem = new JRadioButtonMenuItem("Devagar");
        JRadioButtonMenuItem normalSpeedSubItem = new JRadioButtonMenuItem("Normal");
        JRadioButtonMenuItem fasSpeedSubItem = new JRadioButtonMenuItem("Rápido");
        //animationSpeedSubmenu.add(noAnimationSubItem);
        animationSpeedSubmenu.add(slowSpeedSubItem);
        animationSpeedSubmenu.add(normalSpeedSubItem);
        animationSpeedSubmenu.add(fasSpeedSubItem);

        ButtonGroup speedGroup = new ButtonGroup();
        //speedGroup.add(noAnimationSubItem);
        speedGroup.add(slowSpeedSubItem);
        speedGroup.add(normalSpeedSubItem);
        speedGroup.add(fasSpeedSubItem);

        switch (GameSettings.getAnimationSpeed()) {
            case NONE:
                //noAnimationSubItem.setSelected(true);
                break;
            case SLOW:
                slowSpeedSubItem.setSelected(true);
                break;
            case FAST:
                fasSpeedSubItem.setSelected(true);
                break;
            default:
                normalSpeedSubItem.setSelected(true);
                break;
        }

        //noAnimationSubItem.addActionListener(e -> GameSettings.setAnimationSpeed(AnimationSpeed.NONE));
        slowSpeedSubItem.addActionListener(e -> GameSettings.setAnimationSpeed(AnimationSpeed.SLOW));
        normalSpeedSubItem.addActionListener(e -> GameSettings.setAnimationSpeed(AnimationSpeed.NORMAL));
        fasSpeedSubItem.addActionListener(e -> GameSettings.setAnimationSpeed(AnimationSpeed.FAST));

        boolean defaultSoundFxEnabled = GameSettings.soundEffectsEnabled();
        boolean defaultBgMusicEnabled = GameSettings.backgroundMusicEnabled();

        JCheckBoxMenuItem playSoundFxItem = new JCheckBoxMenuItem("Efeitos sonoros", defaultSoundFxEnabled);
        JCheckBoxMenuItem playBgMusicItem = new JCheckBoxMenuItem("Música de Fundo", defaultBgMusicEnabled);

        playSoundFxItem.addActionListener(e -> {
            boolean enabled = ((JCheckBoxMenuItem) e.getSource()).getState();
            GameSettings.setSoundEffectsEnabled(enabled);
        });

        playBgMusicItem.addActionListener(e -> {
            boolean enabled = ((JCheckBoxMenuItem) e.getSource()).getState();
            GameSettings.setBackgroundMusicEnabled(enabled);

            if (enabled) {
                SoundHandler.getInstance().playBgMusic();
            } else {
                SoundHandler.getInstance().stopBgMusic();
            }
        });

        settingsMenu.add(animationSpeedSubmenu);
        settingsMenu.addSeparator();
        settingsMenu.add(playSoundFxItem);
        settingsMenu.add(playBgMusicItem);

        // Help menu items
        JMenuItem helpItem = new JMenuItem("Sobre o Jogo");
        helpItem.addActionListener(e -> showHelpScreen());
        helpMenu.add(helpItem);

        // Add menus
        menuBar.add(gameMenu);
        menuBar.add(difficultyMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);

        // Set menu bar
        frame.setJMenuBar(menuBar);

        startNewGame();
    }

    private static void initFrame() {
        frame.setLocation(GameSettings.getFrameLocation());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CoalescedEventUpdater updater = new CoalescedEventUpdater(
                400,
                () -> GameSettings.setFrameLocation(frame.getLocation())
        );

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updater.update();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                updater.update();
            }
        });
    }

    private static void startNewGame() {
        int width;
        int height = 480;

        switch (GameSettings.getDifficultyLevel()) {
            case MEDIUM:
                width = 580;
                break;
            case HARD:
                width = 700;
                height = 600;
                break;
            default:
                width = 420;
                break;
        }

        frame.setSize(width, height);
        frame.setResizable(false);

        if (game != null) {
            frame.remove(game);
        }

        game = new MemoryGame(frame);

        frame.add(game);
        frame.revalidate();
        frame.repaint();

        if (!frame.isVisible()) {
            frame.setVisible(true);
        }

        game.newGame();
        SoundHandler.getInstance().playBgMusic();
    }

    private static void showDeckSelector() {
        new DeckSelector(frame);
        SpriteLoader.getInstance(true);
        startNewGame();
    }

    private static void showHelpScreen() {
        new AboutGame(frame);
    }

}
