package dev.corgi.module.impl.misc;

import dev.corgi.module.Category;
import dev.corgi.module.Module;
import net.minecraft.client.Minecraft;

import java.lang.reflect.Field;

public class NickHider extends Module {

    public static Field t = null;
    private String username;

    public NickHider() {
        super("NickHider", "Hides nick dumbass.", Category.MISC);
    }

    @Override
    public void onEnable() {
        try {
            minecraf(true);
            t.set(mc, "meowghost");
        } catch (Exception e2) {
        }
    }

    public void minecraf(boolean avail) throws Exception {
        try {
            t = Minecraft.class.getDeclaredField("field_96683_d");
        } catch (Exception e) {
            t = Minecraft.class.getDeclaredField("displayName");
        }

        if(t != null) {
            if(avail) {
                t.setAccessible(true);
            } else {
                t.setAccessible(false);
            }
        }
    }
}
