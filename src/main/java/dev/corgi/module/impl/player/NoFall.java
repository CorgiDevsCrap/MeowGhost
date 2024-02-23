package dev.corgi.module.impl.player;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NoFall extends Module {

    public NoFall() {
        super("NoFall", "shity packet nofall", Category.PLAYER);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        if ((double)mc.thePlayer.fallDistance > 2.5D) {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        }
    }

}