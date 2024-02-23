package dev.corgi.module.impl.render;

import dev.corgi.module.Category;
import dev.corgi.module.Module;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Chams extends Module {

    public Chams() {
        super("Chams", "see player models thru damn walls!", Category.RENDER);
    }

    @SubscribeEvent
    public void r1(RenderPlayerEvent.Pre e) {
        if(e.entity != mc.thePlayer) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0F, -1100000.0F);
        }
    }

    @SubscribeEvent
    public void r2(RenderPlayerEvent.Post e) {
        if (e.entity != mc.thePlayer) {
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0F, 1100000.0F);
        }
    }

}
