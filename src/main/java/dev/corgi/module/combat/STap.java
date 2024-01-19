package dev.corgi.module.combat;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

public class STap extends Module {

    private long n;
    private long a;
    public Random r;

    public STap() {
        super("STap", "epicer auto S", Category.COMBAT);
        MeowGhost.instance.settingsManager.rSetting(new Setting("Chance", this, 90, 1, 100, false));
        this.n = 0L;
        this.a = 0L;
        this.r = new Random();
    }

    @SubscribeEvent
    public void a(AttackEntityEvent e) {
        if(!mc.thePlayer.isSprinting()) return;
        if((int)MeowGhost.instance.settingsManager.getSettingByName(this, "Chance").getValDouble() >= this.r.nextInt(100) && this.a == 0L && this.n == 0L) {
            this.a = System.currentTimeMillis() + 40L + this.r.nextInt(325);
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent ev) {
        if(MeowGhost.instance.moduleManager.getModule("STap").isToggled()) {
            System.out.println((int) MeowGhost.instance.settingsManager.getSettingByName(this, "Chance").getValDouble());
            if (mc.thePlayer != null && !mc.thePlayer.isSprinting() && this.a > 0L) {
                this.a = 0L;
                return;
            }
            if (System.currentTimeMillis() - this.a > 0L && this.a != 0L) {
                int f = mc.gameSettings.keyBindBack.getKeyCode();
                KeyBinding.setKeyBindState(f, false);
                KeyBinding.onTick(f);
                this.n = System.currentTimeMillis() + 90L + this.r.nextInt(50);
                this.a = 0L;
            } else if (System.currentTimeMillis() - this.n > 0L && this.n != 0L) {
                int g = mc.gameSettings.keyBindBack.getKeyCode();
                KeyBinding.setKeyBindState(g, true);
                KeyBinding.onTick(g);
                this.n = 0L;
            }
        }
    }

}
