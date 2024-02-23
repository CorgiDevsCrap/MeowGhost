package dev.corgi.module.impl.movement;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import org.lwjgl.input.Keyboard;

public class Fly
    extends Module {

    public Setting verusairwalk;

    public Fly() {
        super("Fly", "FLY U STUPID :(", Category.MOVEMENT);
        setKey(Keyboard.KEY_F);
        MeowGhost.instance.settingsManager.rSetting(verusairwalk = new Setting("VerusAirWalk", this, false));
    }

    @Override
    public void onUpdate() {
        if(verusairwalk.getValBoolean()) {
            // TODO: idk
        }
    }
}
