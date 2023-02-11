package util;

import java.awt.*;

public class FontHandler {
    private FontHandler() {
        // Private constructor
    }

    public static Font getFont(int size) {
        return getFont(size, Font.PLAIN);
    }

    public static Font getFont(int size, int style) {
        return new Font("Sans-Serif", style, size);
    }
}
