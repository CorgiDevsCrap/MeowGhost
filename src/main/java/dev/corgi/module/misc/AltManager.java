package dev.corgi.module.misc;

import dev.corgi.MeowGhost;
import dev.corgi.alt.gui.GuiAccountManager;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AltManager extends Module {

    public AltManager() {
     super(
      "AltManager",
      "hit rshift when in multiplayer menu",
      Category.MISC
     );
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e) {
        if(MeowGhost.instance.moduleManager.getModule("AltManager").isToggled()) {
            if (mc.currentScreen instanceof GuiMultiplayer) {
                if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                    mc.displayGuiScreen(new GuiAccountManager(new GuiMultiplayer(new GuiMainMenu())));
                }
            }
        }
    }
}
