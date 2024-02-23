package dev.corgi.module.impl.render;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import dev.corgi.utils.RenderUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.Iterator;

public class PlayerESP
        extends Module {

    private int rgb_c;

    public PlayerESP() {
        super(
                "PlayerESP",
                "chestesp but player",
                Category.RENDER
        );
        MeowGhost.instance.settingsManager.rSetting(new Setting(
                "Red"
                ,this
                ,0
                ,0
                ,255
                ,false
        ));
        MeowGhost.instance.settingsManager.rSetting(new Setting(
                "Green"
                ,this
                ,0
                ,0
                ,255
                ,false
        ));
        MeowGhost.instance.settingsManager.rSetting(new Setting(
                "Blue"
                ,this
                ,0
                ,0
                ,255
                ,false
        ));
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        this.rgb_c = (new Color((int)MeowGhost.instance.settingsManager.getSettingByName(this, "Red").getValDouble(), (int)MeowGhost.instance.settingsManager.getSettingByName(this, "Green").getValDouble(), (int)MeowGhost.instance.settingsManager.getSettingByName(this, "Blue").getValDouble())).getRGB();
    }


    private void r(Entity en, int rgb) {
        RenderUtil.ee(
                en,
                3,
                0.0D,
                0.0D,
                rgb_c,
                false
        );
    }

    @SubscribeEvent
    public void r1(RenderWorldLastEvent e) {
        if (mc.thePlayer != null && mc.theWorld != null && MeowGhost.instance.moduleManager.getModule("PlayerESP").isToggled()) {
            int rgb = this.rgb_c;
            Iterator var3;
                var3 = mc.theWorld.playerEntities.iterator();
                while(true) {
                    EntityPlayer en;
                    do {
                        do {
                            do {
                                if (!var3.hasNext()) {
                                    return;
                                }

                                en = (EntityPlayer)var3.next();
                            } while(en == mc.thePlayer);
                        } while(en.deathTime != 0);
                    } while(!true && en.isInvisible());
                    this.r(en, rgb);
                }
            }
        }
}
