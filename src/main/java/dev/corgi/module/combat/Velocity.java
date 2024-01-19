package dev.corgi.module.combat;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Velocity extends Module {

	public Velocity() {
		super("Velocity", "Reduces knockback", Category.COMBAT);
		MeowGhost.instance.settingsManager.rSetting(new Setting("Horizontal", this, 90, 0, 100, true));
		MeowGhost.instance.settingsManager.rSetting(new Setting("Vertical", this, 100, 0, 100, true));
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e) {
		if (MeowGhost.instance.destructed) {
			return;
		}
		if (mc.thePlayer == null) {
			return;
		}
		float horizontal = (float) MeowGhost.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
		float vertical = (float) MeowGhost.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble();
		
		if (mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime && mc.thePlayer.maxHurtTime > 0) {
			mc.thePlayer.motionX *= (float) horizontal / 100;
			mc.thePlayer.motionY *= (float) vertical / 100;
			mc.thePlayer.motionZ *= (float) horizontal / 100;
		}
	}
}
