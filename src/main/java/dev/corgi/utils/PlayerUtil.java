package dev.corgi.utils;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;

public class PlayerUtil implements MinecraftInstance {

    public static boolean isPlayerInGame() {
        return mc.thePlayer != null && mc.theWorld != null;
    }

    public static void hotkeyToSlot(int slot) {
        if(!isPlayerInGame())
            return;

        mc.thePlayer.inventory.currentItem = slot;
    }

    public static int getCurrentPlayerSlot() {
        return mc.thePlayer.inventory.currentItem;
    }

    public static boolean isAlive() {
        return mc.thePlayer.isEntityAlive();
    }

    public static boolean isInFov(Entity entity, float fov) {
        fov = (float)(fov * 0.5D);
        double v = ((mc.thePlayer.rotationYaw - fovToEntity(entity)) % 360.0D + 540.0D) % 360.0D - 180.0D;
        return ((v > 0.0D && v < fov) || (-fov < v && v < 0.0D));
    }

    public static float fovToEntity(Entity en) {
        double x = en.posX - mc.thePlayer.posX;
        double z = en.posZ - mc.thePlayer.posZ;
        double yaw = Math.atan2(x, z) * 57.2957795D;
        return (float)(yaw * -1.0D);
    }

    public static void swing(boolean legit) {
        EntityPlayerSP p = mc.thePlayer;
        int armSwingEnd = p.isPotionActive(Potion.digSpeed) ? (6 - 1 + p.getActivePotionEffect(Potion.digSpeed).getAmplifier()) : (p.isPotionActive(Potion.digSlowdown) ? (6 + (1 + p.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2) : 6);
        if (!p.isSwingInProgress || p.swingProgressInt >= armSwingEnd / 2 || p.swingProgressInt < 0) {
            p.swingProgressInt = -1;
            p.isSwingInProgress = true;
        }
        if (legit)
            mc.thePlayer.swingItem();
    }

}
