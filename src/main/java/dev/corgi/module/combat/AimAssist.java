package dev.corgi.module.combat;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AimAssist extends Module {

    public AimAssist() {
        super("AimAssist", "epic!", Category.COMBAT);

        MeowGhost.instance.settingsManager.rSetting(new Setting("Speed", this, 1, 35.0, 75.0, false));
        MeowGhost.instance.settingsManager.rSetting(new Setting("FOV", this, 70.0, 30.0, 180.0, false));
        MeowGhost.instance.settingsManager.rSetting(new Setting("Weapon Only", this, false));
        MeowGhost.instance.settingsManager.rSetting(new Setting("ClickAim", this, false));
        MeowGhost.instance.settingsManager.rSetting(new Setting("Teams", this, false));
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(mc.theWorld != null) {
            if(MeowGhost.instance.settingsManager.getSettingByName(this, "Weapon Only").isCheck()) {
                if (mc.thePlayer.getCurrentEquippedItem() == null) {
                    return;
                }
                if(!(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
                    return;
                }
             }
            if(MeowGhost.instance.settingsManager.getSettingByName(this, "ClickAim").isCheck() && !mc.gameSettings.keyBindAttack.isKeyDown()) {
                return;
            }
            final Entity femboyporn = idk_what_this_does_XDDD();
            if(femboyporn != null && (another_one_xd(femboyporn) < - 1.0)) {
                final boolean nigger = another_one_xd(femboyporn) > 0.0;
                final EntityPlayerSP thePlayer = mc.thePlayer;
                thePlayer.rotationYaw += (float)(nigger ? (-(Math.abs(another_one_xd(femboyporn)) / MeowGhost.instance.settingsManager.getSettingByName(this, "FOV").getValDouble())) : (Math.abs(another_one_xd(femboyporn)) / MeowGhost.instance.settingsManager.getSettingByName(this, "Speed").getValDouble() * 20));
            }
        }
    }

    public float deobfuscated_rise61_aimassist_calculations(final Entity ent) {
        final double x = ent.posX - mc.thePlayer.posX;
        final double y = ent.posY - mc.thePlayer.posY;
        final double z = ent.posZ - mc.thePlayer.posZ;
        double yaw = Math.atan2(x, z) * 57.29577951308232;
        yaw = -yaw;
        double pitch = Math.asin(y / Math.sqrt(x * x + y * y + z * z)) * 57.29577951308232;
        pitch = -pitch;
        return (float)yaw;
    }

    public double another_one_xd(final Entity en) {
        return ((mc.thePlayer.rotationYaw - deobfuscated_rise61_aimassist_calculations(en)) % 360.0 + 540.0) % 360.0 - 180.0;
    }

    public boolean rise_410_deobfed_aimassist_LMAO(final Entity en, float a) {
        a *= 0.5;
        final double v = ((mc.thePlayer.rotationYaw - deobfuscated_rise61_aimassist_calculations(en)) % 360.0 + 540.0) % 360.0 - 180.0;
        return (v > 0.0 && v < a) || (-a < v && v < 0.0);
    }

    public boolean novoline_build_from_2020_bullshit(final Entity e) {
        return !(e instanceof EntityLivingBase) || ((EntityLivingBase)e).getTeam() == null || !((EntityLivingBase)e).isOnSameTeam((EntityLivingBase)mc.thePlayer) || MeowGhost.instance.settingsManager.getSettingByName(this, "Teams").isCheck();
    }

    public Entity idk_what_this_does_XDDD() {
        Entity e = null;
        int f = (int)MeowGhost.instance.settingsManager.getSettingByName(this, "Speed").getValDouble() * 20;
        for (final Object o : mc.theWorld.loadedEntityList) {
            final Entity ent = (Entity)o;
            if (ent.isEntityAlive() && !ent.isInvisible() && ent != mc.thePlayer && mc.thePlayer.getDistanceToEntity(ent) < 6.0f && this.novoline_build_from_2020_bullshit(ent) && ent instanceof EntityLivingBase && mc.thePlayer.canEntityBeSeen(ent) && rise_410_deobfed_aimassist_LMAO(ent, (float)f)) {
                e = ent;
                f = (int)another_one_xd(ent);
            }
        }
        return e;
    }

}
