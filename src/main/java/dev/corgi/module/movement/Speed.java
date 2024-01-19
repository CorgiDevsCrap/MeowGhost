package dev.corgi.module.movement;

import dev.corgi.module.Category;
import dev.corgi.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Speed extends Module {

    public Speed() {
        super("Speed", "based speed", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        if(mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
    }

}
