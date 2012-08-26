package tco.daemon;

import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ReferenceConfigs {
	public static int daemonBlockId = 143;
	
	public static int daggerSacrificeId = 5433;
	
	public static void loadConfigs(FMLPreInitializationEvent event){
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try{
			cfg.load();
			daemonBlockId = cfg.getOrCreateBlockIdProperty("BlockDaemon", daemonBlockId).getInt();
			daggerSacrificeId = cfg.getOrCreateIntProperty("daggerSacrifice", Configuration.CATEGORY_ITEM, daggerSacrificeId).getInt();
		}catch(Exception e){
			FMLLog.log(Level.SEVERE, e, "Failed to load Daemon mod configs.");
		}finally{
			cfg.save();
		}
	}

}
