package dev.corgi.utils;

public class InvUtils implements MinecraftInstance {

    public static int getCurrentPlayerSlot() {
        return mc.thePlayer.inventory.currentItem;
    }

    public static void hotkeyToSlot(int slot) {
        if (!PlayerUtil.isPlayerInGame())
            return;
        mc.thePlayer.inventory.currentItem = slot;
    }

}
