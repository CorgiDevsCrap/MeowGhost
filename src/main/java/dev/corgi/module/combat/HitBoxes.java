package dev.corgi.module.combat;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import net.minecraft.entity.Entity;

import java.util.List;

public class HitBoxes extends Module {

    public double eDouble;
    public float eFloat;

    public HitBoxes() {
        super("HitBoxes", "MEOW!", Category.COMBAT);
        MeowGhost.instance.settingsManager.rSetting(new Setting("Expand", this, 0.0D, 0.0D, 1.0D, false));
    }

    public Entity entity() {
        Entity en = null;
        if(mc.thePlayer.worldObj != null) {
            for (final Object o: mc.theWorld.loadedEntityList) {
                en = (Entity)o;
            }
        }
        return en;
    }

    @Override
    public void onUpdate() {
        final Entity renderViewEntity = mc.getRenderViewEntity();
        final List targetList = mc.theWorld.loadedEntityList;
        final Entity ent = this.entity();
        for(final Object aTargetList : targetList) {
            final Entity target = (Entity)aTargetList;
            if(target != mc.thePlayer && target.canBeCollidedWith()) {
                target.width = (float)(1.0 + MeowGhost.instance.settingsManager.getSettingByName(this, "Expand").getValDouble() * 4);
            }
        }
    }

}
