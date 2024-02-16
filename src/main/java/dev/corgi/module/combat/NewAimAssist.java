package dev.corgi.module.combat;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import dev.corgi.utils.PlayerUtil;
import ibxm.Player;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import org.lwjgl.input.Mouse;

import java.util.Iterator;

public class NewAimAssist
    extends Module {

    public Setting speed;
    public Setting fov;
    public Setting distance;
    public Setting clickAim;
    public Setting weaponOnly;
    public Setting aimInvis;
    public Setting blatantMode;

    public NewAimAssist() {
        super("AimAssist", "Welcome to the AA Meeting, Always attacking.", Category.COMBAT);
        MeowGhost.instance.settingsManager.rSetting(speed = new Setting("Speed", this, 45.0D, 1.0D, 100.0D, false));
        MeowGhost.instance.settingsManager.rSetting(fov  = new Setting("FOV", this, 90.0D, 15.0D, 180.0D, false));
        MeowGhost.instance.settingsManager.rSetting(distance = new Setting("Distance", this, 4.5D, 1.0D, 10.0D, false));
        MeowGhost.instance.settingsManager.rSetting(clickAim = new Setting("ClickAim", this, true));
        MeowGhost.instance.settingsManager.rSetting(weaponOnly = new Setting("WeaponOnly", this, true));
        MeowGhost.instance.settingsManager.rSetting(aimInvis = new Setting("AimInvis", this, true));
        MeowGhost.instance.settingsManager.rSetting(blatantMode = new Setting("BlatantMode", this, true));
    }

    @Override
    public void onUpdate() {
        if (mc.currentScreen == null && mc.inGameHasFocus) {
            if(weaponOnly.getValBoolean()) {
                if (mc.thePlayer.getCurrentEquippedItem() == null) {
                    return;
                }
                if (!(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
                    return;
                }
            }
            if(clickAim.getValBoolean()) {
                if(!mc.gameSettings.keyBindAttack.isKeyDown() && !Mouse.isButtonDown(0)) {
                    return;
                }
            }
            Entity en = this.getEnemy();
                    if (en != null) {
                        if (blatantMode.getValBoolean()) {
                            PlayerUtil.aim(en, 0.0F, false);
                        } else {
                            double n = PlayerUtil.n(en);
                            if (n > 1.0D || n < -1.0D) {
                                float val = (float) (-(n / (101.0D - speed.getValDouble())));
                                mc.thePlayer.rotationYaw += val;
                            }
                        }
                    }
        }
    }

    public Entity getEnemy() {
        int fov1 = (int) fov.getValDouble();
        Iterator var2 = mc.theWorld.playerEntities.iterator();

        EntityPlayer en;
        do {
            do {
                do {
                    do {
                        do {
                            do {
                                if (!var2.hasNext()) {
                                    return null;
                                }

                                en = (EntityPlayer) var2.next();
                            } while (en == mc.thePlayer);
                        } while (en.deathTime != 0);
                    } while (!en.getDisplayNameString().contains("ITEM SHOP") || !en.getDisplayNameString().contains("TEAM UPGRADES"));
                    } while(!aimInvis.getValBoolean() && en.isInvisible());
                } while((double)mc.thePlayer.getDistanceToEntity(en) > distance.getValDouble());
        } while(blatantMode.getValBoolean() && !PlayerUtil.isInFov(en, (float)fov1));

        return en;
    }
}
