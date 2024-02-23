package dev.corgi.utils;

import dev.corgi.MeowGhost;
import dev.corgi.module.impl.combat.KillAura;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import static dev.corgi.utils.MinecraftInstance.mc;

public class CombatUtils {

    public static boolean isEntityInPlayerRange(Entity en, double range) {
        if (en == null || en.isDead || en.isInvisibleToPlayer((EntityPlayer)mc.thePlayer))
            return false;
        return (mc.thePlayer.getDistanceToEntity(en) < range);
    }

    public static float[] getTargetRotations(Entity en) {
        double diffY;
        if (en == null)
            return null;
        double diffX = en.posX - mc.thePlayer.posX;
        if (en instanceof EntityLivingBase) {
            EntityLivingBase x = (EntityLivingBase)en;
            diffY = x.posY + x.getEyeHeight() * 0.9D - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
        } else {
            diffY = ((en.getEntityBoundingBox()).minY + (en.getEntityBoundingBox()).maxY) / 2.0D - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
        }
        double diffZ = en.posZ - mc.thePlayer.posZ;
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float)-(Math.atan2(diffY, MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ)) * 180.0D / Math.PI);
        return new float[] { mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch +
                MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch) };
    }

    public static void aimAtPlayer(Entity en, float off, boolean silent, int m) throws InstantiationException, IllegalAccessException {
        if (en != null) {
            float[] rots = getTargetRotations(en);
            if (rots != null) {
                float yaw = rotsToFloat(rots, 1);
                float pitch = rotsToFloat(rots, 2) + 4.0F + off;
                if (silent) {
                    if (m == 1)
                        silentRotations(yaw);
                    if (m == 2) {
                        if (MeowGhost.instance.settingsManager.getSettingByName(KillAura.class.newInstance(), "RayCast").isCheck())
                            RayCastUtils.rayCast(MeowGhost.instance.settingsManager.getSettingByName(KillAura.class.newInstance(), "AttackRange").getValDouble(), yaw, pitch);
                        silentRotations(en);
                    }
                } else {
                    mc.thePlayer.rotationYaw = yaw;
                    mc.thePlayer.rotationPitch = pitch;
                }
            }
        }
    }

    public static float rotsToFloat(float[] rots, int m) {
        if (m == 1)
            return rots[0];
        if (m == 2)
            return rots[1] + 4.0F;
        return -1.0F;
    }


    public static void silentRotations(float bodyRot, float headRot) {
        if (headRot > bodyRot)
            return;
        mc.thePlayer.renderYawOffset = bodyRot;
        mc.thePlayer.rotationYawHead = headRot;
    }

    public static void silentRotations(float yaw) {
        mc.thePlayer.renderYawOffset = yaw;
        mc.thePlayer.rotationYawHead = yaw;
    }

    public static void silentRotations(float headRot, boolean a) {
        mc.thePlayer.rotationYawHead = headRot;
    }

    public static void silentRotations(Entity en) {
        if (en != null && en != mc.thePlayer) {
            float yaw = getTargetRotations(en)[0];
            if (yaw != Float.NaN) {
                mc.thePlayer.renderYawOffset = yaw;
                mc.thePlayer.setRotationYawHead(yaw + (float)Math.random());
            }
        }
    }

    public static void attackPlayer(Entity en, int m) {
        if (en == mc.thePlayer || en == null || en.isDead)
            return;
        if (m == 1)
            mc.playerController.attackEntity((EntityPlayer)mc.thePlayer, en);
        if (m == 2) {
            PlayerUtil.swing(true);
            mc.getNetHandler().addToSendQueue((Packet)new C02PacketUseEntity(en, C02PacketUseEntity.Action.ATTACK));
        }
        if (m == 3) {
            AttackEntityEvent e = new AttackEntityEvent((EntityPlayer)mc.thePlayer, en);
            mc.playerController.attackEntity((EntityPlayer)mc.thePlayer, e.target);
        }
        if (mc.thePlayer.fallDistance > 0.0F)
            mc.thePlayer.onCriticalHit(en);
    }

}
