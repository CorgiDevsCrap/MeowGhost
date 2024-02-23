package dev.corgi.module.impl.misc;

import dev.corgi.module.Category;
import dev.corgi.MeowGhost;
import dev.corgi.module.Module;

public class SelfDestruct extends Module {

	public SelfDestruct() {
		super("SelfDestruct", "Destructs", Category.MISC);
	}

	@Override
	public void onEnable() {
		MeowGhost.instance.onDestruct();
	}
}
