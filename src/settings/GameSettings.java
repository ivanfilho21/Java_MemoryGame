package settings;

import settings.enums.AnimationSpeed;
import settings.enums.DeckType;
import settings.enums.DifficultyLevel;

import java.awt.*;
import  java.util.prefs.*;

public class GameSettings {
    private static final String TRUE = "T";
    private static final String FALSE = "F";
    private static final String FRAME_LOCATION_FORMAT = "%d&%d";
    private static final String SETTING_DECK_TYPE = "setting.deck.type";
    private static final String SETTING_FRAME_LOCATION = "setting.frame.location";
    private static final String SETTING_DIFFICULTY = "setting.difficulty";
    private static final String SETTING_ANIMATION_SPEED = "setting.animation.speed";
    private static final String SETTING_SOUND_EFFECTS = "setting.sound.effects";
    private static final String SETTING_BG_MUSIC = "setting.background.music";

    private GameSettings() {
        // Private constructor
    }

    public static void setFrameLocation(Point location) {
        set(SETTING_FRAME_LOCATION, String.format(FRAME_LOCATION_FORMAT, location.x, location.y));
    }

    public static Point getFrameLocation() {
        String text = get(SETTING_FRAME_LOCATION, String.format(FRAME_LOCATION_FORMAT, 0, 0));
        String[] split = text.split("&");
        Point location = new Point();

        if (split.length >= 1) {
            location.x = Integer.parseInt(split[0]);
        }

        if (split.length >= 2) {
            location.y = Integer.parseInt(split[1]);
        }

        return location;
    }

    public static DeckType getDeckType() {
        switch (get(SETTING_DECK_TYPE, DeckType.YUGI.getValue())) {
            case "1": return DeckType.KAIBA;
            case "2": return DeckType.JOEY;
            case "3": return DeckType.PEGASUS;
            case "4": return DeckType.STAPLE;
            default: return DeckType.YUGI;
        }
    }

    public static void setDeckType(DeckType deck) {
        set(SETTING_DECK_TYPE, deck.getValue());
    }

    public static DifficultyLevel getDifficultyLevel() {
        switch (get(SETTING_DIFFICULTY, DifficultyLevel.EASY.getValue())) {
            case "1": return DifficultyLevel.MEDIUM;
            case "2": return DifficultyLevel.HARD;
            default: return DifficultyLevel.EASY;
        }
    }

    public static void setDifficultyLevel(DifficultyLevel level) {
        set(SETTING_DIFFICULTY, level.getValue());
    }

    public static AnimationSpeed getAnimationSpeed() {
        switch (get(SETTING_ANIMATION_SPEED, AnimationSpeed.NORMAL.getValue())) {
            case "0": return AnimationSpeed.NONE;
            case "1": return AnimationSpeed.SLOW;
            case "3": return AnimationSpeed.FAST;
            default: return AnimationSpeed.NORMAL;
        }
    }

    public static void setAnimationSpeed(AnimationSpeed speed) {
        set(SETTING_ANIMATION_SPEED, speed.getValue());
    }

    public static boolean soundEffectsEnabled() {
        return get(SETTING_SOUND_EFFECTS, TRUE).equalsIgnoreCase(TRUE);
    }

    public static void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        set(SETTING_SOUND_EFFECTS, soundEffectsEnabled ? TRUE : FALSE);
    }

    public static boolean backgroundMusicEnabled() {
        return get(SETTING_BG_MUSIC, TRUE).equalsIgnoreCase(TRUE);
    }

    public static void setBackgroundMusicEnabled(boolean bgMusicEnabled) {
        set(SETTING_BG_MUSIC, bgMusicEnabled ? TRUE : FALSE);
    }

    private static String get(String key, String defaultValue) {
        Preferences prefs = Preferences.userNodeForPackage(GameSettings.class);
        return prefs.get(key, defaultValue);
    }

    private static void set(String key, String value) {
        Preferences prefs = Preferences.userNodeForPackage(GameSettings.class);
        prefs.put(key, value);
    }

}
