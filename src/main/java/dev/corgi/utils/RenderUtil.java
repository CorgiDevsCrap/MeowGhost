package dev.corgi.utils;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL11;

public class RenderUtil implements MinecraftInstance {

    /*
	public static void drawPicture(float x, float y, float w, float h, int color, String location) {
		GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        ResourceLocation resourceLocation = new ResourceLocation("moonlight/" + location);

        GL11.glColor4f(1,1,1,1);
        
        if(color != 0) {
        	color(color);
        }
        
        mc.getTextureManager().bindTexture(resourceLocation);
        
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, w, h, w, h);
        GlStateManager.disableBlend();
    }

     */
    
    public static void drawHead(int x, int y, int w, int h, int color, AbstractClientPlayer player) {
        final ResourceLocation skin = player.getLocationSkin();
        
        mc.getTextureManager().bindTexture(skin);

        Gui.drawScaledCustomSizeModalRect(x, y, 8, 8, 8, 8, w, h, 64, 64);
    }
    
    public static void drawCircle(double x, double y, double r, double h, double j, int color) {
        GL11.glEnable(GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        glBegin(GL_TRIANGLE_FAN);

        color(color);

        double var;
        glVertex2d(x, y);
        for (var = h; var <= j; var++) {
            color(color);
            glVertex2d(
                    (float) (r * Math.cos(Math.PI * var / 180) + x),
                    (float) (r * Math.sin(Math.PI * var / 180) + y)
            );
        }

        glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL_BLEND);
    }

    public static void drawRect(double x, double y, double w, double h, int color) {
        Gui.drawRect((int) x, (int) y, (int) (x + w), (int) (y + h), color);
    }
    
    public static void drawRoundedRect(double x, double y, double w, double h, int radius, int color, int index) {
        glEnable(GL_LINE_SMOOTH);
        if (index == -1) {
            drawRect(x, y, w, h, color);
        } else if (index == 0) {
            drawRect(x + radius, y + radius, w - radius * 2, h - radius * 2, color);
            drawRect(x + radius, y, w - radius * 2, radius, color);
            drawRect(x + w - radius, y + radius, radius, h - radius * 2, color);
            drawRect(x + radius, y + h - radius, w - radius * 2, radius, color);
            drawRect(x, y + radius, radius, h - radius * 2, color);
            drawCircle(x + radius, y + radius, radius, 180, 270, color);
            drawCircle(x + w - radius, y + radius, radius, 270, 360, color);
            drawCircle(x + radius, y + h - radius, radius, 90, 180, color);
            drawCircle(x + w - radius, y + h - radius, radius, 0, 90, color);
        } else if (index == 1) {
            drawRect(x + radius, y, w - radius * 2, radius, color);
            drawRect(x, y + radius, w, h - radius, color);
            drawCircle(x + radius, y + radius, radius, 180, 270, color);
            drawCircle(x + w - radius, y + radius, radius, 270, 360, color);
        } else if (index == 2) {
            drawRect(x, y, w, h - radius, color);
            drawRect(x + radius, y + h - radius, w - radius * 2, radius, color);
            drawCircle(x + radius, y + h - radius, radius, 90, 180, color);
            drawCircle(x + w - radius, y + h - radius, radius, 0, 90, color);
        }
        glDisable(GL_LINE_SMOOTH);
    }
    
    private static void drawBordered(final double x, final double y, final double x2, final double y2, final double l1, final int col1, final int col2) {
        Gui.drawRect((int) x, (int) y, (int) x2, (int) y2, col2);
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GlStateManager.color(f2, f3, f4, f);
        GL11.glLineWidth((float) l1);
        GL11.glBegin(1);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void drawBorderedRect(final double x, final double y, final double width, final double height, final double thick, final int col1, final int col2) {
        drawBordered(x, y, x + width, y + height, thick, col1, col2);
    }
    
    public static void color(int color) {
        float alpha = (color >> 24 & 255) / 255f;
        float red = (color >> 16 & 255) / 255f;
        float green = (color >> 8 & 255) / 255f;
        float blue = (color & 255) / 255f;
        GlStateManager.color(red, green, blue, alpha);
    }
}
