package dev.corgi.utils;

import net.minecraft.client.Minecraft;
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

    public static net.minecraft.util.Timer gt() {
        try {
            return (net.minecraft.util.Timer)ReflectUtil.t.get(Minecraft.getMinecraft());
        } catch (IndexOutOfBoundsException | IllegalAccessException var1) {
            return null;
        }
    }

    public static int getColor(long speed, long... delay) {
        long time = System.currentTimeMillis() + (delay.length > 0 ? delay[0] : 0L);
        return Color.getHSBColor((float)(time % (15000L / speed)) / (15000.0F / (float)speed), 1.0F, 1.0F).getRGB();
    }

}
