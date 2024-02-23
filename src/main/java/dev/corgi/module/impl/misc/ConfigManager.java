package dev.corgi.module.impl.misc;

import dev.corgi.autosave.GuiConfigManager;
import dev.corgi.friends.GuiAddFriend;
import dev.corgi.module.Category;
import dev.corgi.module.Module;

public class ConfigManager extends Module {

    public ConfigManager() {
     super(
      "ConfigManager",
      "idk",
      Category.MISC
     );
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new GuiConfigManager());
        toggle();
    }
}
