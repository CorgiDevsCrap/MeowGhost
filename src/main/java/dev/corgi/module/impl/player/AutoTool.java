package dev.corgi.module.impl.player;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import dev.corgi.utils.CoolDown;
import dev.corgi.utils.InvUtils;
import dev.corgi.utils.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.util.concurrent.ThreadLocalRandom;

public class AutoTool
    extends Module {

    public Setting hotkeyBack;
    private Block previousBlock;
    private boolean isWaiting;
    public Setting mineDelay;
    public int previousSlot;
    public boolean justFinishedMining;
    public boolean mining;
    public CoolDown delay;

    public AutoTool() {
        super("AutoTool", "r u stupid", Category.PLAYER);
        MeowGhost.instance.settingsManager.rSetting(hotkeyBack = new Setting("HotkeyBack", this, false));
        MeowGhost.instance.settingsManager.rSetting(mineDelay = new Setting("MaxDelay", this, 10.0D, 1.0D, 100.0D, true));
        delay = new CoolDown(0L);
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent e) {
        if (!PlayerUtil.isPlayerInGame() || mc.currentScreen != null)
            return;
        if (!Mouse.isButtonDown(0)) {
            if (mining)
                finishMining();
            if (this.isWaiting)
                this.isWaiting = false;
            return;
        }
        BlockPos lookingAtBlock = mc.objectMouseOver.getBlockPos();
        if (lookingAtBlock != null) {
            Block stateBlock = mc.theWorld.getBlockState(lookingAtBlock).getBlock();
            if (stateBlock != Blocks.air && !(stateBlock instanceof net.minecraft.block.BlockLiquid) && stateBlock instanceof Block) {
                if (mineDelay.getMin() > 0.0D) {
                    if (this.previousBlock != null) {
                        if (this.previousBlock != stateBlock) {
                            this.previousBlock = stateBlock;
                            this.isWaiting = true;
                            delay.setCooldown((long) ThreadLocalRandom.current().nextDouble(mineDelay.getMin(), mineDelay.getMax() + 0.01D));
                            delay.start();
                        } else if (this.isWaiting && delay.hasFinished()) {
                            this.isWaiting = false;
                            previousSlot = InvUtils.getCurrentPlayerSlot();
                            mining = true;
                            hotkeyToFastest();
                        }
                    } else {
                        this.previousBlock = stateBlock;
                        this.isWaiting = false;
                    }
                    return;
                }
                if (!mining) {
                    previousSlot = InvUtils.getCurrentPlayerSlot();
                    mining = true;
                }
                hotkeyToFastest();
            }
        }
    }

    public void finishMining() {
        if (this.hotkeyBack.getValBoolean())
            InvUtils.hotkeyToSlot(previousSlot);
        justFinishedMining = false;
        mining = false;
    }

    private void hotkeyToFastest() {
        int index = -1;
        double speed = 1.0D;
        for (int slot = 0; slot <= 8; slot++) {
            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
            if (itemInSlot != null && (
                    itemInSlot.getItem() instanceof net.minecraft.item.ItemTool || itemInSlot.getItem() instanceof net.minecraft.item.ItemShears)) {
                BlockPos p = mc.objectMouseOver.getBlockPos();
                Block bl = mc.theWorld.getBlockState(p).getBlock();
                if (itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState()) > speed) {
                    speed = itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState());
                    index = slot;
                }
            }
        }
        if (index != -1 && speed > 1.1D && speed != 0.0D)
            InvUtils.hotkeyToSlot(index);
    }

}
