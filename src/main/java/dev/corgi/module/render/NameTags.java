package dev.corgi.module.render;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import dev.corgi.utils.ClientUtil;
import dev.corgi.utils.PlayerUtil;
import dev.corgi.utils.TextFormatting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent.Specials.Pre;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import javax.xml.soap.Text;

public class NameTags extends Module {

    public Setting a;
    public Setting b;
    public Setting c;
    public Setting d;
    public Setting rm;

    public NameTags() {
        super("NameTags", "epico name tago amigos", Category.RENDER);
        MeowGhost.instance.settingsManager.rSetting(a = new Setting("Offset", this, 0.0D,-40.0D, 40.0D, false));
        MeowGhost.instance.settingsManager.rSetting(b = new Setting("Rect", this, true));
        MeowGhost.instance.settingsManager.rSetting(c = new Setting("ShowHealth", this, true));
        MeowGhost.instance.settingsManager.rSetting(d = new Setting("ShowInvis", this, true));
        MeowGhost.instance.settingsManager.rSetting(rm = new Setting("RemoveTags", this, true));
    }

    @SubscribeEvent
    public void r(Pre e) {
        if(rm.isCheck()) {
            e.setCanceled(true);
        } else {
            if(e.entity instanceof EntityPlayer && e.entity != mc.thePlayer && e.entity.deathTime == 0) {
                EntityPlayer en = (EntityPlayer) e.entity;
                if(!d.isCheck() && en.isInvisible()) {
                    return;
                }

                if(en.getDisplayNameString().isEmpty()) {
                    return;
                }

                e.setCanceled(true);
                String str = en.getDisplayName().getFormattedText();
                if(c.isCheck()) {
                    double r = (double)(en.getHealth() / en.getMaxHealth());
                    String h = (r < 0.3D ? TextFormatting.COLOR_CHAR + "c" : (r < 0.5D ? TextFormatting.COLOR_CHAR + "6" : (r > 0.7D ? TextFormatting.COLOR_CHAR + "e" : TextFormatting.COLOR_CHAR + "a"))) + ClientUtil.rnd((double)en.getHealth(), 1);
                    str = str + " " + h;
                }

                GlStateManager.pushMatrix();
                GlStateManager.translate((float)e.x + 0.0F, (float)e.y + en.height + 0.5F, (float)e.z);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
                float f1 = 0.02666667F;
                GlStateManager.scale(-f1, -f1, f1);
                if (en.isSneaking()) {
                    GlStateManager.translate(0.0F, 9.374999F, 0.0F);
                }

                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);
                GlStateManager.disableDepth();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                int i = (int)(-a.getValDouble());
                int j = mc.fontRendererObj.getStringWidth(str) / 2;
                GlStateManager.disableTexture2D();
                if (b.isCheck()) {
                    worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                    worldrenderer.pos((double)(-j - 1), (double)(-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    worldrenderer.pos((double)(-j - 1), (double)(8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    worldrenderer.pos((double)(j + 1), (double)(8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    worldrenderer.pos((double)(j + 1), (double)(-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator.draw();
                }

                GlStateManager.enableTexture2D();
                mc.fontRendererObj.drawString(str, -mc.fontRendererObj.getStringWidth(str) / 2, i, -1);
                GlStateManager.enableDepth();
                GlStateManager.depthMask(true);
                GlStateManager.enableLighting();
                GlStateManager.disableBlend();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.popMatrix();
            }
        }
    }
}
