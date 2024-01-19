package dev.corgi.module.render;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import dev.corgi.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class HUD extends Module {

	public HUD() {
		super("HUD", "Draws the module list on your screen", Category.RENDER);
	}


	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent egoe) {
		if (!egoe.type.equals(egoe.type.CROSSHAIRS) || MeowGhost.instance.destructed) {
			return;
		}

		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
		List<Module> sortedModules = MeowGhost.instance.moduleManager.getModuleList().stream()
				.filter(obj -> obj instanceof Module)
				.map(obj -> (Module) obj)
				.filter(mod -> !mod.getName().equalsIgnoreCase("HUD") && mod.isToggled() && mod.visible)
				.sorted(Comparator.<Module, Integer>comparing(mod -> fr.getStringWidth(mod.getName())).reversed())
				.collect(Collectors.toList());

		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int y = 2;

		if(MeowGhost.instance.moduleManager.getModule("HUD").isToggled()) {
			for (Module mod : sortedModules) {
				fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - 6, y + 5, -1);
				y += fr.FONT_HEIGHT;
			}
			int height = fr.FONT_HEIGHT - 5;
			String text = MeowGhost.cn.toLowerCase() + " " + MeowGhost.cv + " | " + mc.getSession().getUsername() + " | " + Minecraft.getDebugFPS();
			RenderUtil.drawRoundedRect((sr.getScaledWidth() - sr.getScaledWidth() + 10), height + 3,fr.getStringWidth(text) + 10,fr.FONT_HEIGHT + 2,5,new Color(0,0,0,60).getRGB(),0);
			fr.drawStringWithShadow(text, (sr.getScaledWidth() - sr.getScaledWidth() + 15), height + 5 ,-1);
		}


		/*
		for (Module mod : MeowGhost.instance.moduleManager.getModuleList()) {
			if (!mod.getName().equalsIgnoreCase("HUD") && mod.isToggled() && mod.visible) {
				FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
				fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - 6, y + 5, -1);
				y += fr.FONT_HEIGHT;
			}
		}
		 */
	}
}