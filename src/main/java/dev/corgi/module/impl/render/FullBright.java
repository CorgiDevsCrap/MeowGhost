package dev.corgi.module.impl.render;

import dev.corgi.module.Category;
import dev.corgi.module.Module;

public class FullBright extends Module {

    public FullBright() {
        super("FullBright", "see in dark dummy", Category.RENDER);
    }

    @Override
    public void onEnable() {
        mc.gameSettings.gammaSetting = 69420;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = 1;
    }

}
