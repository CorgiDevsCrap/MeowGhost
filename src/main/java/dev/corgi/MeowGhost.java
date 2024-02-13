package dev.corgi;
import dev.corgi.module.Module;
import dev.corgi.module.ModuleManager;
import dev.corgi.utils.ReflectUtil;
import dev.corgi.utils.RenderUtil;
import org.lwjgl.input.Keyboard;
import dev.corgi.autosave.SaveLoad;
import dev.corgi.clickgui.ClickGui;
import dev.corgi.settings.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
public class MeowGhost
{
    public static MeowGhost instance;
    public ModuleManager moduleManager;
    public SettingsManager settingsManager;
    public ClickGui clickGui;
    public SaveLoad saveLoad;
    public boolean destructed = false;
	public static String cn = "MeowGhost";
	public static String cv = "v1.9";
    
    public void init() {
    	MinecraftForge.EVENT_BUS.register(this);
    	settingsManager = new SettingsManager();
    	moduleManager = new ModuleManager();
    	clickGui = new ClickGui();
    	saveLoad = new SaveLoad();
		ReflectUtil.su();
    }
    
    @SubscribeEvent
    public void key(KeyInputEvent e) {
    	if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null)
    		return; 
    	try {
             if (Keyboard.isCreated()) {
                 if (Keyboard.getEventKeyState()) {
                     int keyCode = Keyboard.getEventKey();
                     if (keyCode <= 0)
                    	 return;
                     for (Module m : moduleManager.modules) {
                    	 if (m.getKey() == keyCode && keyCode > 0) {
                    		 m.toggle();
                    	 }
                     }
                 }
             }
         } catch (Exception q) { q.printStackTrace(); }
    }
    
    public void onDestruct() {
    	if (Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().thePlayer != null) {
    		Minecraft.getMinecraft().thePlayer.closeScreen();
    	}
    	destructed = true;
    	MinecraftForge.EVENT_BUS.unregister(this);
    	for (int k = 0; k < this.moduleManager.modules.size(); k++) {
    		Module m = this.moduleManager.modules.get(k);
    		MinecraftForge.EVENT_BUS.unregister(m);
    		this.moduleManager.getModuleList().remove(m);
    	}
    	this.moduleManager = null;
    	this.clickGui = null;
    }
}
