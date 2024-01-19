package dev.corgi.utils;

import java.awt.*;

public class ColorUtil {

    public static int astolfoColorsDraw(int yOffset, int yTotal) {
        return astolfoColorsDraw(yOffset, yTotal, 2900F);
    }

    public static int astolfoColorsDraw(int yOffset, int yTotal, float speed) {
        float hue = (float) (System.currentTimeMillis() % (int) speed) + ((yTotal - yOffset) * 9);
        while (hue > speed)
            hue -= speed;
        hue /= speed;
        if (hue > 0.5)
            hue = 0.5F - (hue - 0.5f);
        hue += 0.5F;
        return Color.HSBtoRGB(hue, 0.5f, 1F);
    }

}
