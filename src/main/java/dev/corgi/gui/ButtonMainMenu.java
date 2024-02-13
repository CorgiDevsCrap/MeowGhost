package dev.corgi.gui;

import java.awt.Color;

import dev.corgi.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class ButtonMainMenu {

	private int x,y,width,height;
	
	private final String name;
	
	public ButtonMainMenu(String name, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.name = name;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {		
		RenderUtil.drawRoundedRect(x, y, width, height, 1 , isHover(mouseX, mouseY) ? -1 : new Color(14,14,14).getRGB(), 0);
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(name, x + (width / 2 ) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(name) / 2), y + (height / 2) - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2), isHover(mouseX, mouseY) ? new Color(14,14,14).getRGB() : -1);
		//Fonts.productSans18.drawString(name, x + (width / 2) - (Fonts.productSans18.getStringWidth(name) / 2), y + (height / 2) -
		//		(Fonts.productSans18.getHeight() / 2), isHover(mouseX, mouseY) ? new Color(14,14,14).getRGB() : -1);
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isHover(float mouseX, float mouseY) {
		if(mouseX > x && mouseX <= x + width && mouseY > y && mouseY <= y + height) {
			return true;
		}
		
		return false;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
	
}
