package dev.corgi.module.movement;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Flight extends Module {

    public Flight() {
        super("Flight", "fly in air retard, WITH NO CREATIVE HACKE!!!", Category.MOVEMENT);
        MeowGhost.instance.settingsManager.rSetting(new Setting("Creative", this, false));
        MeowGhost.instance.settingsManager.rSetting(new Setting("NoYChange", this, false));
        MeowGhost.instance.settingsManager.rSetting(new Setting("ViewBob", this, false));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if(mc.thePlayer != null) {
            if (MeowGhost.instance.settingsManager.getSettingByName(this, "Creative").isCheck()) {
                mc.thePlayer.capabilities.allowFlying = true;
                mc.thePlayer.capabilities.isFlying = true;
            } else {
                return;
            }
            if (MeowGhost.instance.settingsManager.getSettingByName(this, "NoYChange").isCheck()) {
                mc.thePlayer.capabilities.allowFlying = false;
                mc.thePlayer.capabilities.isFlying = false;
            } else {
                return;
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if(mc.thePlayer != null) {
            if (MeowGhost.instance.settingsManager.getSettingByName(this, "Creative").isCheck()) {
                mc.thePlayer.capabilities.allowFlying = false;
                mc.thePlayer.capabilities.isFlying = false;
            } else {
                return;
            }
        }
    }

    @SubscribeEvent
    public void a(TickEvent.PlayerTickEvent e) {
        if(mc.thePlayer != null) {
            if (MeowGhost.instance.moduleManager.getModule("Flight").isToggled()) {
                if (MeowGhost.instance.settingsManager.getSettingByName(this, "NoYChange").isCheck()) {
                    mc.thePlayer.motionY = 0;
                } else {
                    return;
                }
                if (MeowGhost.instance.settingsManager.getSettingByName(this, "ViewBob").isCheck()) {
                    mc.thePlayer.cameraYaw = mc.thePlayer.cameraPitch = 0.1f;
                } else {
                    return;
                }
            }
        }
    }
}
