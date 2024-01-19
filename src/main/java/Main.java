import dev.corgi.MeowGhost;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "timechanger", version = "1.1")
public class Main {

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	MeowGhost.instance = new MeowGhost();
    	MeowGhost.instance.init();
    }
}
