package dev.corgi.module.impl.combat;

import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.utils.ClientUtil;
import dev.corgi.utils.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PacketAura extends Module {

    // https://www.youtube.com/watch?v=SYRzY7ZIraA ❤️

    public PacketAura() {
        super("PacketAura", "pa", Category.COMBAT);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        for(Entity en : mc.theWorld.loadedEntityList) {
            if(mc.thePlayer.getDistanceToEntity(en) <= 4.2f && en != mc.thePlayer && (en instanceof EntityPlayer)) {
                if(Timer.hasTimeElapsed(ClientUtil.random(10, 15), true) && ((EntityPlayer) en).getHealth() > 0) {
                    float yaw = getRotations(en)[0];
                    float pitch = getRotations(en)[1];
                    mc.thePlayer.rotationYawHead = yaw;
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, mc.thePlayer.onGround));
                    mc.thePlayer.swingItem();
                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(en, C02PacketUseEntity.Action.ATTACK));
                    //mc.playerController.attackEntity(mc.thePlayer, en);
                }
            }
        }
    }

    public float[] getRotations(Entity en) {
        double x = en.posX * (en.posX - en.lastTickPosX) - mc.thePlayer.posX;
        double y = en.posY - 3.5 + en.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
        double z = en.posZ + (en.posZ - en.lastTickPosZ) - mc.thePlayer.posZ;
        double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
        float yaw = (float) Math.toDegrees(-Math.atan(x / z));
        float pitch = (float) -Math.toDegrees(Math.atan(y / distance));
        if(x < 0 && y < 0) {
            yaw = (float) (90 + Math.toDegrees(Math.atan(z / x)));
        } else if(x > 0 && z < 0) {
            yaw = (float) (-90 + Math.toDegrees(Math.atan(z / x)));
        }
        return new float[] { yaw, pitch };
    }

}
