package dev.corgi.module.impl.combat;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.utils.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;

public class DelayRemover extends Module {
    private Field l = null;

    public DelayRemover() {
        super("DelayRemover", "funne", Category.COMBAT);
    }

    public void onEnable() {
        try {
            this.l = Minecraft.class.getDeclaredField("field_71429_W");
        } catch (Exception var4) {
            try {
                this.l = Minecraft.class.getDeclaredField("leftClickCounter");
            } catch (Exception var3) {
            }
        }

        if (this.l != null) {
            this.l.setAccessible(true);
        } else {
            MeowGhost.instance.moduleManager.getModule("DelayRemover").setToggled(false);
        }

    }

    @SubscribeEvent
    public void a(TickEvent.PlayerTickEvent b) {
        if (PlayerUtil.isPlayerInGame() && this.l != null) {
            if (!mc.inGameHasFocus || mc.thePlayer.capabilities.isCreativeMode) {
                return;
            }

            try {
                this.l.set(mc, 0);
            } catch (IllegalAccessException | IndexOutOfBoundsException var3) {
            }
        }

    }

}
