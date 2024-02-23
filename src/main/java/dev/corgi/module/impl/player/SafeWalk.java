package dev.corgi.module.impl.player;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import dev.corgi.utils.PlayerUtil;
import ibxm.Player;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SafeWalk
    extends Module {

    public Setting a;
    public Setting b;
    private boolean c = false;
    private boolean d = false;

    public SafeWalk() {
        super("SafeWalk", "MEOW", Category.PLAYER);
        MeowGhost.instance.settingsManager.rSetting(a = new Setting("Shift", this, true));
        MeowGhost.instance.settingsManager.rSetting(b = new Setting("Blocks only", this, true));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.sh(false);
        c = false;
        d = false;
    }

    @SubscribeEvent
    public void p(TickEvent.PlayerTickEvent e) {
        if (PlayerUtil.isPlayerInGame() && a.isCheck()) {
            if (mc.thePlayer.onGround) {
                if (PlayerUtil.currentPos()) {
                    if (b.isCheck()) {
                        ItemStack i = mc.thePlayer.getHeldItem();
                        if (i == null || !(i.getItem() instanceof ItemBlock)) {
                            if (d) {
                                d = false;
                                this.sh(false);
                            }

                            return;
                        }
                    }

                    d = true;
                    this.sh(true);
                    c = true;
                } else if (d) {
                    d = false;
                    this.sh(false);
                }
            }

            if (c && mc.thePlayer.capabilities.isFlying) {
                this.sh(false);
                c = false;
            }

        }
    }

    private void sh(boolean sh) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), sh);
    }

}
