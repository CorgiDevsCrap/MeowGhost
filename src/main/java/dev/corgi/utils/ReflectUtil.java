package dev.corgi.utils;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

import java.awt.event.MouseEvent;
import java.lang.reflect.Field;

public class ReflectUtil {

    public static Field t = null;
    public static Field g = null;
    public static Field f = null;
    public static Field h = null;

    public static void su() {
        try {
            t = Minecraft.class.getDeclaredField("field_71428_T");
        } catch (Exception var4) {
            try {
                t = Minecraft.class.getDeclaredField("timer");
            } catch (Exception var3) {
            }
        }

        if (t != null) {
            t.setAccessible(true);
        }

        try {
            g = MouseEvent.class.getDeclaredField("button");
            f = MouseEvent.class.getDeclaredField("buttonstate");
            h = Mouse.class.getDeclaredField("buttons");
        } catch (Exception var2) {
        }

    }

}
