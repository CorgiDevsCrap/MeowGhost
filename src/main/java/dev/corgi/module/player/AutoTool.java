package dev.corgi.module.player;

import dev.corgi.module.Category;
import dev.corgi.module.Module;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AutoTool extends Module {

    private boolean isBreakingBlock = false;

    public AutoTool() {
        super("AutoTool", "kill me", Category.PLAYER);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player != null) {
            handleAutoTool(event.player);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        // Handle render tick event if needed
        // You can use event.renderTickTime as the partial ticks value
    }

    private void handleAutoTool(EntityPlayer player) {
        BlockPos targetPos = getTargetBlockPos(player);

        if (targetPos != null) {
            Block targetBlock = player.worldObj.getBlockState(targetPos).getBlock();

            // Check if the player is actively breaking a block
            if (player.isSwingInProgress && player.swingProgressInt > 0) {
                isBreakingBlock = true;
            } else if (isBreakingBlock) {
                isBreakingBlock = false;

                // If not holding a valid tool, try to find one in the inventory and switch to it
                if (!isPlayerHoldingValidTool(player)) {
                    switchToValidToolInInventory(player);
                }

                // Simulate tool usage
                simulateToolUsage(player, targetPos);
            }
        }
    }


    private void switchToValidToolInInventory(EntityPlayer player) {
        for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++) {
            ItemStack inventoryStack = player.inventory.getStackInSlot(slot);
            if (inventoryStack != null) {
                Item inventoryItem = inventoryStack.getItem();
                if (inventoryItem instanceof ItemTool || inventoryItem instanceof ItemShears) {
                    // Switch to the found tool in the inventory
                    player.inventory.currentItem = slot;
                    Minecraft.getMinecraft().playerController.updateController();
                    break;
                }
            }
        }
    }

    private boolean isPlayerHoldingValidTool(EntityPlayer player) {
        ItemStack heldItem = player.getHeldItem();

        // Add your logic to check if the held item is a valid tool (e.g., pickaxe, shovel)
        if (heldItem != null) {
            Item heldItemItem = heldItem.getItem();

            return heldItemItem instanceof ItemTool || heldItemItem instanceof ItemShears;
        } else {
            // Player is not holding a valid tool, try to find one in the inventory
            for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++) {
                ItemStack inventoryStack = player.inventory.getStackInSlot(slot);
                if (inventoryStack != null) {
                    Item inventoryItem = inventoryStack.getItem();
                    if (inventoryItem instanceof ItemTool || inventoryItem instanceof ItemShears) {
                        // Switch to the found tool in the inventory
                        player.inventory.currentItem = slot;
                        Minecraft.getMinecraft().playerController.updateController();
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private BlockPos getTargetBlockPos(EntityPlayer player) {
        MovingObjectPosition target = getMouseOver(player, 8.0);

        if (target != null && target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            return target.getBlockPos();
        }

        return null;
    }

    private void simulateToolUsage(EntityPlayer player, BlockPos targetPos) {
        player.worldObj.getBlockState(targetPos).getBlock().onBlockClicked(player.worldObj, targetPos, player);
    }

    private MovingObjectPosition getMouseOver(EntityPlayer player, double reach) {
        double posX = player.prevPosX + (player.posX - player.prevPosX);
        double posY = player.prevPosY + (player.posY - player.prevPosY) + player.getEyeHeight();
        double posZ = player.prevPosZ + (player.posZ - player.prevPosZ);

        net.minecraft.util.Vec3 lookVec = player.getLookVec();
        net.minecraft.util.Vec3 startVec = new net.minecraft.util.Vec3(posX, posY, posZ);
        net.minecraft.util.Vec3 endVec = startVec.addVector(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach);

        return player.worldObj.rayTraceBlocks(startVec, endVec);
    }

}
