package tco.daemon;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import net.minecraft.src.Item;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ReferenceConfigs {
	public static int daemonBlockId = 143;

	public static int daggerSacrificeId = 5433,
			birdCannnonId = 5434;

	public static int orbMoldId = 5633,
			orbGlassId = 5634,
			orbObsidianId = 5635,
			orbBlazeId = 5636,
			orbWolfId = 5637,
			orbUnstableId = 5638;

	public static void loadConfigs(FMLPreInitializationEvent event) {
		Configuration cfg = new Configuration(
				event.getSuggestedConfigurationFile());
		try {
			cfg.load();
			daemonBlockId = cfg.getOrCreateBlockIdProperty("BlockDaemon",
					daemonBlockId).getInt();

			daggerSacrificeId = cfg.getOrCreateIntProperty("daggerSacrifice",
					Configuration.CATEGORY_ITEM, daggerSacrificeId).getInt();
			birdCannnonId = cfg.getOrCreateIntProperty("birdCannnon",
					Configuration.CATEGORY_ITEM, birdCannnonId).getInt();

			orbMoldId = cfg.getOrCreateIntProperty("orbMold",
					Configuration.CATEGORY_ITEM, orbMoldId).getInt();
			orbGlassId = cfg.getOrCreateIntProperty("orbGlass",
					Configuration.CATEGORY_ITEM, orbGlassId).getInt();
			orbObsidianId = cfg.getOrCreateIntProperty("orbObsidian",
					Configuration.CATEGORY_ITEM, orbObsidianId).getInt();
			orbBlazeId = cfg.getOrCreateIntProperty("orbBlaze",
					Configuration.CATEGORY_ITEM, orbBlazeId).getInt();
			orbWolfId = cfg.getOrCreateIntProperty("orbWolf",
					Configuration.CATEGORY_ITEM, orbWolfId).getInt();
			orbUnstableId = cfg.getOrCreateIntProperty("orbUnstable",
					Configuration.CATEGORY_ITEM, orbUnstableId).getInt();
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "Failed to load Daemon mod configs.");
		} finally {
			cfg.save();
		}
	}
	
	public static Properties props;

	public static void loadLocalizationProps() {
		InputStream stream;
		try {
			stream = ReferenceConfigs.class
					.getResourceAsStream("/tco/daemon/lang/en_US.properties");
			Properties props = new Properties();
			props.load(stream);
			for(Object key : props.keySet()){
				LanguageRegistry.instance().addStringLocalization((String)key, "en_US", (String)props.get(key));
			}
			stream.close();
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e,
					"Failed to load Daemon mod language files.");
		}
	}
	
	public String get(String key){
		return props.getProperty(key);
	}

}
