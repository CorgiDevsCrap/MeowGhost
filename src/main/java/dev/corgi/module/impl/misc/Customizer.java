package dev.corgi.module.impl.misc;

import dev.corgi.MeowGhost;
import dev.corgi.alt.gui.GuiAccountManager;
import dev.corgi.gui.MainMenu;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Customizer
    extends Module {

    private String title;

    public Customizer() {
        super("Customizer", "customizes", Category.MISC);
        MeowGhost.instance.settingsManager.rSetting(new Setting("Shader", this, true));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        title = Display.getTitle();
        titleCustomizer(true);
    }


    @Override
    public void onDisable() {
        super.onDisable();
        titleCustomizer(false);
    }

    public void titleCustomizer(boolean choice) {
        if(choice) {
            Display.setTitle(MeowGhost.cn + " " + MeowGhost.cv);
        } else if(!choice) {
            Display.setTitle(title);
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e) {
        if(MeowGhost.instance.moduleManager.getModule("Customizer").isToggled()) {
            if (mc.currentScreen instanceof GuiMainMenu) {
                mc.displayGuiScreen(new MainMenu());
            }
        }
    }

}
