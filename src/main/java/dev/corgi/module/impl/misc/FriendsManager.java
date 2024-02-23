package dev.corgi.module.impl.misc;

import dev.corgi.MeowGhost;
import dev.corgi.alt.gui.GuiAccountManager;
import dev.corgi.friends.GuiAddFriend;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class FriendsManager extends Module {

    public FriendsManager() {
     super(
      "FriendsManager",
      "idk",
      Category.MISC
     );
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new GuiAddFriend());
        toggle();
    }
}
