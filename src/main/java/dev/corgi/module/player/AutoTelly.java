package dev.corgi.module.player;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import dev.corgi.utils.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoTelly extends Module {
    enum Phase {
        PreJump, Turn, Jump, Jumping, Placing;
    }
    public Setting delay;
    private Phase phase = Phase.PreJump;
    private float startYaw;
    private float startPitch;
    private BlockPos lastBlock;
    public AutoTelly() {
        super("AutoTelly", "tellys for u bro", Category.PLAYER);
        MeowGhost.instance.settingsManager.rSetting(delay = new Setting("Delay", this, 2.5D, 1, 512, false));
    }

    @Override
    public void onEnable() {
        if(PlayerUtil.isPlayerInGame() && mc.inGameHasFocus) {
            this.startYaw = mc.thePlayer.rotationYaw;
            this.startPitch = mc.thePlayer.rotationPitch;
            this.phase = Phase.PreJump;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
            this.lastBlock = isAirBlock(getBlock((new BlockPos((Entity) this.mc.thePlayer)).down())) ? null : (new BlockPos((Entity)this.mc.thePlayer)).down();
        }
    }

    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post e) {
        if(this.phase.equals(Phase.Placing))
            placeBlock((int)mc.playerController.getBlockReachDistance(), false, e.partialTicks);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (this.lastBlock == null) {
            return;
        }
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
        if (Math.sqrt(mc.thePlayer.getDistanceSq(this.lastBlock)) > delay.getValDouble()) {
            this.phase = Phase.Placing;
        } else if (!isAirBlock(getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.2D, mc.thePlayer.posZ))) && this.phase.equals(Phase.Placing)) {
            this.phase = Phase.Turn;
        } else if (mc.thePlayer.isCollidedVertically && this.phase.equals(Phase.Turn)) {
            this.phase = Phase.Jump;
        }
        switch (this.phase) {
            case Turn:
                mc.thePlayer.rotationYaw = this.startYaw;
                mc.thePlayer.rotationPitch = this.startPitch;
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
            case PreJump:
                mc.thePlayer.rotationYaw = this.startYaw;
                mc.thePlayer.rotationPitch = this.startPitch;
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
                this.phase = Phase.Jump;
                break;
            case Jump:
                mc.thePlayer.rotationYaw = this.startYaw;
                mc.thePlayer.rotationPitch = this.startPitch;
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
                this.phase = Phase.Jumping;
                break;
            case Jumping:
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
                break;
            case Placing:
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
                placeBlock((int)mc.playerController.getBlockReachDistance(), true, 0.0F);
                break;
        }
    }

    public boolean placeBlockSimple(BlockPos pos, boolean place, float partialTicks) {
        Entity entity = mc.getRenderViewEntity();
        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosZ) * partialTicks;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosY) * partialTicks;
        Vec3 eyesPos = new Vec3(d0, d1 + mc.thePlayer.getEyeHeight(), d2);
        for (EnumFacing side : EnumFacing.values()) {
            if (!side.equals(EnumFacing.UP))
                if (!side.equals(EnumFacing.DOWN)) {
                    BlockPos neighbor = pos.offset(side);
                    EnumFacing side2 = side.getOpposite();
                    if (getBlock(neighbor).canCollideCheck(mc.theWorld.getBlockState(neighbor), false)) {
                        Vec3 hitVec = (new Vec3((Vec3i)neighbor)).addVector(0.5D, 0.5D, 0.5D).add(new Vec3(side2.getDirectionVec()));
                        if (eyesPos.squareDistanceTo(hitVec) <= 36.0D) {
                            float[] angles = getRotations(neighbor, side2, partialTicks);
                            (mc.getRenderViewEntity()).rotationYaw = angles[0];
                            (mc.getRenderViewEntity()).rotationPitch = angles[1];
                            if (place) {
                                mc.thePlayer.rotationYaw = angles[0];
                                mc.thePlayer.rotationPitch = angles[1];
                                mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem(), neighbor, side2, hitVec);
                                mc.thePlayer.swingItem();
                                this.lastBlock = pos;
                            }
                            return true;
                        }
                    }
                }
        }
        return false;
    }

    public float[] getRotations(BlockPos block, EnumFacing face, float partialTicks) {
        Entity entity = mc.getRenderViewEntity();
        double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
        double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
        double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
        double x = block.getX() + 0.5D - posX + face.getFrontOffsetX() / 2.0D;
        double z = block.getZ() + 0.5D - posZ + face.getFrontOffsetZ() / 2.0D;
        double y = block.getY() + 0.5D;
        double d1 = posY + mc.thePlayer.getEyeHeight() - y;
        double d3 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float)(Math.atan2(d1, d3) * 180.0D / Math.PI);
        if (yaw < 0.0F)
            yaw += 360.0F;
        return new float[] { yaw, pitch };
    }

    public boolean placeBlock(int range, boolean place, float partialTicks) {
        if (!isAirBlock(getBlock((new BlockPos((Entity)mc.thePlayer)).down())))
            return true;
        if (placeBlockSimple((new BlockPos((Entity)mc.thePlayer)).down(), place, partialTicks)) {
            if (place)
                this.lastBlock = (new BlockPos((Entity)mc.thePlayer)).down();
            return true;
        }
        int dist = 0;
        Block target = null;
        while (dist <= range) {
            int blockDist = 0;
            while (dist != blockDist) {
                int x = blockDist;
                while (x >= 0) {
                    int z = blockDist - x;
                    int y = dist - blockDist;                                                    // kanye
                    if (placeBlockSimple((new BlockPos((Entity)this.mc.thePlayer)).down(y).north(x).west(z), place, partialTicks))
                        return true;
                    if (placeBlockSimple((new BlockPos((Entity)this.mc.thePlayer)).down(y).north(x).west(-z), place, partialTicks))
                        return true;
                    if (placeBlockSimple((new BlockPos((Entity)this.mc.thePlayer)).down(y).north(-x).west(z), place, partialTicks))
                        return true;
                    if (placeBlockSimple((new BlockPos((Entity)this.mc.thePlayer)).down(y).north(-x).west(-z), place, partialTicks))
                        return true;
                    x--;
                }
                blockDist++;
            }
            dist++;
        }
        return false;
    }

    public boolean isAirBlock(Block block) {
        if (block.getMaterial().isReplaceable()) {
            if (block instanceof net.minecraft.block.BlockSnow && block.getBlockBoundsMaxY() > 0.125D)
                return false;
            return true;
        }
        return false;
    }

    public static Block getBlock(BlockPos pos) {
        return (mc).theWorld.getBlockState(pos).getBlock();
    }

}
