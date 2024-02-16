package dev.corgi.module.combat;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

public class  Reach extends Module {

    public int miInt;
    public int maInt;
    public double miDouble;
    public double maDouble;
    public Random r;

    public Reach() {
        super("Reach", "catgirls", Category.COMBAT);
        this.r = new Random();
        MeowGhost.instance.settingsManager.rSetting(new Setting("Min", this, 3.0, 3.0, 4.0, false));
        MeowGhost.instance.settingsManager.rSetting(new Setting("Max", this, 3.0, 3.0, 4.0, false));
        MeowGhost.instance.settingsManager.rSetting(new Setting("Blatant", this, true));
        MeowGhost.instance.settingsManager.rSetting(new Setting("Random", this, true));
        MeowGhost.instance.settingsManager.rSetting(new Setting("Misplaced", this, true));
    }

    @SubscribeEvent
    public void a(final MouseEvent event) {
        if(!MeowGhost.instance.settingsManager.getSettingByName(this, "Blatant").getValBoolean() && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            return;
        }
        final double lol = miDouble + this.r.nextDouble() * (maDouble - miDouble);
        final Object[] meow = getPlayers(lol, 0.0, 0.0f);
        if(meow == null) {
            return;
        }
        mc.objectMouseOver = new MovingObjectPosition((Entity) meow[0], (Vec3) meow[1]);
        mc.pointedEntity = (Entity) meow[0];
    }

    @Override
    public void onEnable() {
        vals();
    }

    public Object[] getPlayers(final double d, final double expand, final float partialTicks) {
        final Entity renderViewEntity = mc.getRenderViewEntity();
        Entity entity = null;
        if(renderViewEntity == null || mc.theWorld == null) {
            return null;
        }
        mc.mcProfiler.startSection("pick");
        final Vec3 eyePosition = renderViewEntity.getPositionEyes(0.0F);
        final Vec3 lookVal = renderViewEntity.getLook(0.0F);
        final Vec3 newEyePos = eyePosition.addVector(lookVal.xCoord * d, lookVal.yCoord * d, lookVal.zCoord * d);
        Vec3 hitVec = null;
        final List targetList = mc.theWorld.getEntitiesWithinAABBExcludingEntity(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(lookVal.xCoord * d, lookVal.yCoord * d, lookVal.zCoord * d).expand(1.0, 1.0, 1.0));
        double reach = d;
        for(int i = 0; i < targetList.size(); ++i) {
            final Entity target = (Entity) targetList.get(i);
            if(target.canBeCollidedWith()) {
                final float collisionSize = target.getCollisionBorderSize();
                AxisAlignedBB newTargetBB = target.getEntityBoundingBox().expand((double)collisionSize, (double) collisionSize, (double) collisionSize);
                newTargetBB = newTargetBB.expand(expand, expand, expand);
                final MovingObjectPosition mop = newTargetBB.calculateIntercept(eyePosition, newEyePos);
                if(newTargetBB.isVecInside(eyePosition)) {
                    if(0.0 < reach || reach == 0.0) {
                        entity = target;
                        hitVec = ((mop == null) ? eyePosition : mop.hitVec);
                        reach = 0.0;
                    }
                }
                else if(mop != null) {
                    final double distanceToTarget = eyePosition.distanceTo(mop.hitVec);
                    if(distanceToTarget < reach || reach == 0.0) {
                        final boolean canRiderInteract = false;
                        if(target == renderViewEntity.ridingEntity) {
                            if(reach == 0.0) {
                                entity = target;
                                hitVec = mop.hitVec;
                            }
                        }
                    }
                    else {
                        entity = target;
                        hitVec = mop.hitVec;
                        reach = distanceToTarget;
                    }
                }
            }
        }
        if(reach < d && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
        }
        mc.mcProfiler.endSection();
        if(entity == null || hitVec == null) {
            return null;
        }
        return new Object[] { entity, hitVec };
    }

    public void vals() {
        miDouble = MeowGhost.instance.settingsManager.getSettingByName(this, "Min").getValDouble();
        maDouble = MeowGhost.instance.settingsManager.getSettingByName(this, "Max").getValDouble();
        miInt = (int) miDouble;
        maInt = (int) maDouble;
    }

}
