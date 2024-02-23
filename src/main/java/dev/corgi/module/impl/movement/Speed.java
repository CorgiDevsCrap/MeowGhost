package dev.corgi.module.impl.movement;

import dev.corgi.module.Category;
import dev.corgi.module.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Speed extends Module {

    private boolean jumping;
    private boolean running;

    public Speed() {
        super("Speed", "based speed", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        this.running = true;
        this.jumping = false;
    }

    @Override
    public void onUpdate() {
        if(!this.jumping && mc.thePlayer.onGround) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
            jumping = true;
        } if (this.jumping && !mc.thePlayer.onGround){
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
            jumping = false;
        }
    }

    @Override
    public void onDisable() {
        this.running = false;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
        this.jumping = false;
    }

}
