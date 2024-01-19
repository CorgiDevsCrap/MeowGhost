package dev.corgi.module.player;

import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;
import java.util.Random;

public class FastPlace extends Module {
    public static Field r = null;
    protected Minecraft mc = Minecraft.getMinecraft();

    public FastPlace() {
        super("FastPlace", "epic!", Category.PLAYER);
    }

    @SubscribeEvent
    public void a(final TickEvent.PlayerTickEvent e) {
        try {
            r = mc.getClass().getDeclaredField("field_71467_ac");
        } catch (Exception ex1) {
            try {
                r = mc.getClass().getDeclaredField("rightClickDelayTimer");
            } catch (Exception ex2) {
            }
        }

        try {
            if(r != null) {
                r.setAccessible(true);
                r.set(mc, 0);
            }
        } catch (Exception ex3) {
        }
    }

}
