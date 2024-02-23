import dev.corgi.MeowGhost;
import dev.corgi.timechanger.TimeChanger;
import dev.corgi.timechanger.TimeChangerNetHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = "timechanger", version = "2.3")
public class Main {

    @EventHandler
    public void init(FMLInitializationEvent event) {
        TimeChanger.instance = new TimeChanger();
        TimeChanger.instance.init();
    	MeowGhost.instance = new MeowGhost();
    	MeowGhost.instance.init();
    }
}
