package dev.corgi.module.combat;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoClicker extends Module {

	private long lastClick;
	private long hold;
	
	private double speed;
	private double holdLength;
	private double min;
	private double max;
	public boolean hasSelectedBlock = false;

	public AutoClicker() {
		super("AutoClicker", "Automatically clicks when you hold down left click", Category.COMBAT);

		MeowGhost.instance.settingsManager.rSetting(new Setting("MinCPS", this, 8, 1, 20, false));
		MeowGhost.instance.settingsManager.rSetting(new Setting("MaxCPS", this, 12, 1, 20, false));
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.RenderTickEvent e) {
		if(this.getIfSelectingBlock(this.hasSelectedBlock)) {
			return;
		}
		if (MeowGhost.instance.destructed) {
			return;
		}
		if (Mouse.isButtonDown(0)) {
			if (System.currentTimeMillis() - lastClick > speed * 1000) {
				lastClick = System.currentTimeMillis();
				if (hold < lastClick) {
					hold = lastClick;
				}
				int key = mc.gameSettings.keyBindAttack.getKeyCode();
				KeyBinding.setKeyBindState(key, true);
				KeyBinding.onTick(key);
				this.updateVals();
			} else if (System.currentTimeMillis() - hold > holdLength * 1000) {
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
				this.updateVals();
			}
		}
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		this.updateVals();
	}
	
	private void updateVals() {
		min = MeowGhost.instance.settingsManager.getSettingByName(this, "MinCPS").getValDouble();
		max = MeowGhost.instance.settingsManager.getSettingByName(this, "MaxCPS").getValDouble();
		
		if (min >= max) {
			max = min + 1;
		}
		
		speed = 1.0 / ThreadLocalRandom.current().nextDouble(min - 0.2, max);
		holdLength = speed / ThreadLocalRandom.current().nextDouble(min, max);
	}

	private boolean getIfSelectingBlock(boolean c) {
		if (Minecraft.getMinecraft().getRenderViewEntity() != null) {
			final Entity player = (Entity)AutoClicker.mc.thePlayer;
			if (player != null) {
				final MovingObjectPosition mop = Minecraft.getMinecraft().getRenderViewEntity().rayTrace(4.8, 1.0f);
				if (mop != null) {
					final World world = AutoClicker.mc.thePlayer.worldObj;
					if (world != null) {
						final BlockPos pos = new BlockPos(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
						if (pos != null) {
							final Material mat = world.getBlockState(pos).getBlock().getMaterial();
							if (mat != null) {
								if (Minecraft.getMinecraft().objectMouseOver.entityHit != null) {
									c = false;
								}
								else if (mop.typeOfHit != MovingObjectPosition.MovingObjectType.MISS) {
									c = true;
								}
								else if (mop.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
									c = false;
								}
							}
						}
					}
				}
			}
		}
		return c;
	}
}
