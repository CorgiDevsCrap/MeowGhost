package dev.corgi.module.render;

import dev.corgi.MeowGhost;
import dev.corgi.module.Category;
import dev.corgi.module.Module;
import dev.corgi.settings.Setting;
import dev.corgi.utils.ColorUtil;
import dev.corgi.utils.RenderUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Iterator;

public class ChestESP
    extends Module {

    public ChestESP() {
        super(
                "ChestESP",
                "esp for chests",
                Category.RENDER
        );
        MeowGhost.instance.settingsManager.rSetting(new Setting(
                "Red"
                ,this
                ,0
                ,0
                ,255
                ,false
        ));
        MeowGhost.instance.settingsManager.rSetting(new Setting(
                "Green"
                ,this
                ,0
                ,0
                ,255
                ,false
        ));
        MeowGhost.instance.settingsManager.rSetting(new Setting(
                "Blue"
                ,this
                ,0
                ,0
                ,255
                ,false
        ));
    }

    @SubscribeEvent
    public void o(RenderWorldLastEvent ev) {
        if(mc.thePlayer != null
                && mc.theWorld != null
                && !MeowGhost.instance.destructed
                && MeowGhost.instance.moduleManager.getModule("ChestESP").isToggled())
            {
            int rgb = new Color(
                    (int)MeowGhost.instance.settingsManager.getSettingByName(this, "Red").getValDouble(),
                    (int)MeowGhost.instance.settingsManager.getSettingByName(this, "Green").getValDouble(),
                    (int)MeowGhost.instance.settingsManager.getSettingByName(this, "Blue").getValDouble()
                ).getRGB();
                Iterator var3 = mc.theWorld.loadedTileEntityList.iterator();

                while(true) {
                    TileEntity te;
                    do {
                        if(!var3.hasNext()) {
                            return;
                        }

                        te = (TileEntity)var3.next();
                    } while(!(te instanceof TileEntityChest) && !(te instanceof TileEntityEnderChest));

                    RenderUtil.re(te.getPos(), rgb, true);
                }
        }
    }

}
