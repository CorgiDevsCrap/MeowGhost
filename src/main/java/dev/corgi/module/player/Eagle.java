package dev.corgi.module.player;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Eagle extends Module {

    public Eagle() {
        super("Eagle", "eagler", Category.PLAYER);
        this.setKey(Keyboard.KEY_G);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(mc.thePlayer.onGround && MeowGhost.instance.moduleManager.getModule("Eagle").isToggled()) {
            ItemStack i = mc.thePlayer.getCurrentEquippedItem();
            BlockPos bP = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1D, mc.thePlayer.posZ);
            if(i != null) {
                if(i.getItem() instanceof ItemBlock) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
                    if(mc.theWorld.getBlockState(bP).getBlock() == Blocks.air && mc.thePlayer.onGround) {
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                    }
                }
            }
        }
    }

    public void onDisable() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
    }

}
