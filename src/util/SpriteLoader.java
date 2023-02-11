package util;

import settings.GameSettings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SpriteLoader {
    public static final int CARDS_PER_DECK = 12;
    private static SpriteLoader instance = null;
    private final List<Image> spriteSheet;
    private final List<Image> characters;
    private final List<Image> images;

    public static SpriteLoader getInstance() {
        return getInstance(false);
    }

    public static SpriteLoader getInstance(boolean reset) {
        if (instance == null || reset) {
            instance = new SpriteLoader();
        }
        return instance;
    }

    private SpriteLoader() {
        int cardOriginalSize = 100;
        int cardOutputSize = 75;
        int charOriginalWidth = 110;
        int charOriginalHeight = 96;
        spriteSheet = new ArrayList<>();
        characters = new ArrayList<>();
        images = new ArrayList<>();

        BufferedImage cover = readImage("cover.jpg");
        BufferedImage cards = readImage("cards.jpg");
        BufferedImage charset = readImage("chars.jpg");

        spriteSheet.add(scaleImage(cover, cardOutputSize));

        int deckIndex = Integer.parseInt(GameSettings.getDeckType().getValue());

        for (int x = 0; x < CARDS_PER_DECK; x++) {
            BufferedImage card =
                    cards.getSubimage(x * cardOriginalSize, deckIndex * cardOriginalSize, cardOriginalSize, cardOriginalSize);

            spriteSheet.add(scaleImage(card, cardOutputSize));
        }

        for (int x = 0; x < 4; x++) {
            BufferedImage character = charset.getSubimage(x * charOriginalWidth, 0, charOriginalWidth, charOriginalHeight);
            characters.add(scaleImage(character, cardOutputSize));
        }
        characters.add(
                scaleImage(
                        cards.getSubimage(0, 4 * cardOriginalSize, cardOriginalSize, cardOriginalSize),
                        cardOutputSize
                )
        );

        images.add(readImage("bg1.jpg"));
        images.add(readImage("icon.png"));
    }

    private BufferedImage readImage(String image) {
        BufferedImage img = null;
        URL url = SpriteLoader.class.getResource("/assets/" + image);

        if (url == null) {
            System.err.println("\nImage not found in the project.");
        } else {
            try {
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return img;
    }

    private Image scaleImage(BufferedImage image, int size) {
        return image.getScaledInstance(size, size, Image.SCALE_FAST);
    }

    public List<Image> getSpriteSheet() {
        return spriteSheet;
    }

    public int getSpritesQuantity() {
        return spriteSheet.size();
    }

    public List<Image> getCharacters() {
        return characters;
    }

    public List<Image> getImages() {
        return images;
    }
}
