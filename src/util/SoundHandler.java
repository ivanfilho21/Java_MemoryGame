package util;

import settings.GameSettings;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.net.URL;

public class SoundHandler {
    private static SoundHandler instance = null;
    private Clip bgClip = null;

    private SoundHandler() {
        try {
            bgClip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            System.out.println("Error playing audio.");
        }
    }

    public static SoundHandler getInstance() {
        if (instance == null) {
            instance = new SoundHandler();
        }
        return instance;
    }

    public void playSuccessSE() {
        playSE("success");
    }

    public void playWinGameSE() {
        playSE("won");
    }

    public void playDealCardSE() {
        playSE("deal");
    }

    public void playBgMusic() {
        if (!GameSettings.backgroundMusicEnabled()) {
            return;
        }

        try {
            URL fileUrl = SoundHandler.class.getResource("/assets/bg.wav");

            if (fileUrl == null) {
                System.out.println("BG music not found.");
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileUrl);

            stopBgMusic();
            bgClip = AudioSystem.getClip();
            bgClip.open(audioInputStream);
            bgClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error playing BG music.");
        }
    }

    public void stopBgMusic() {
        if (bgClip != null && bgClip.isRunning()) {
            bgClip.stop();
        }
    }

    private void playSE(String fileName) {
        if (!GameSettings.soundEffectsEnabled()) {
            return;
        }

        try {
            URL fileUrl = SoundHandler.class.getResource("/assets/" + fileName + ".wav");

            if (fileUrl == null) {
                System.out.println("SE \"" + fileName + ".wav\" not found.");
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileUrl);

            Clip seClip = AudioSystem.getClip();
            seClip.open(audioInputStream);
            seClip.start();
        } catch (Exception e) {
            System.out.println("Error playing SE.");
        }
    }

}
