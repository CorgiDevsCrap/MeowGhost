package dev.corgi.module;
import java.util.ArrayList;

import dev.corgi.module.impl.combat.*;
import dev.corgi.module.impl.misc.AltManager;
import dev.corgi.module.impl.misc.Customizer;
import dev.corgi.module.impl.misc.FriendsManager;
import dev.corgi.module.impl.misc.SelfDestruct;
import dev.corgi.module.impl.movement.Fly;
import dev.corgi.module.impl.movement.Speed;
import dev.corgi.module.impl.movement.Sprint;
import dev.corgi.module.impl.player.*;
import dev.corgi.module.impl.render.*;

public class ModuleManager {

	public ArrayList<Module> modules;
	
	public ModuleManager() {
		(modules = new ArrayList<Module>()).clear();
		this.modules.add(new ClickGUI());
		this.modules.add(new HUD());
		this.modules.add(new Sprint());
		this.modules.add(new Speed());
		this.modules.add(new AutoClicker());
		this.modules.add(new Velocity());
		this.modules.add(new AntiBot());
		this.modules.add(new SelfDestruct());
		this.modules.add(new Eagle());
		this.modules.add(new Chams());
		this.modules.add(new HitBoxes());
		this.modules.add(new Reach());
		//this.modules.add(new AimAssist());
		this.modules.add(new FullBright());
		this.modules.add(new FastPlace());
		this.modules.add(new ChestESP());
		this.modules.add(new PlayerESP());
		this.modules.add(new AltManager());
		this.modules.add(new Customizer());
		this.modules.add(new DelayRemover());
		this.modules.add(new NoFall());
        this.modules.add(new KillAura());
		this.modules.add(new PacketAura());
		this.modules.add(new SafeWalk());
		this.modules.add(new NewAimAssist());
		this.modules.add(new FriendsManager());
		this.modules.add(new Fly());
		this.modules.add(new AutoTool());
	}
	
	public Module getModule(String name) {
		for (Module m : this.modules) {
			if (m.getName().equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}

	public ArrayList<Module> getModuleList() {
		return this.modules;
	}
	
	public ArrayList<Module> getModulesInCategory(Category c) {
		ArrayList<Module> mods = new ArrayList<Module>();
		for (Module m : this.modules) {
			if (m.getCategory() == c) {
				mods.add(m);
			}
		}
		return mods;
	}
}
