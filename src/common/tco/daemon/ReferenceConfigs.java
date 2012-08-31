package tco.daemon;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ReferenceConfigs {
	public static final String VERSION = "0.1";
	public static final boolean DEBUG = true;
	
	public static final String TEXTURE_BLOCKS = "/tco/daemon/sprites/blocks.png",
			TEXTURE_ITEMS = "/tco/daemon/sprites/daemonitems.png",
			GUI_MATRIX = "/tco/daemon/sprites/matrix.png",
			GUI_FEEDER = "/tco/daemon/sprites/feeder.png",
			GUI_HUNGER_CHEST = "/gui/container.png";
			
	
	public static int blockDaemonId = 143,
			blockBrazierId = 144;
	
	public static int daemonBrazierId = 5432;

	public static int daggerSacrificeId = 5433,
			daggerRitualId = 5434,
			birdCannnonId = 5435;

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
			blockDaemonId = cfg.getOrCreateBlockIdProperty("blockDaemon",
					blockDaemonId).getInt();
			blockBrazierId = cfg.getOrCreateBlockIdProperty("blockBrazier",
					blockBrazierId).getInt();
			
			daemonBrazierId = cfg.getOrCreateIntProperty("daemonBrazier",
					Configuration.CATEGORY_ITEM, daemonBrazierId).getInt();

			daggerSacrificeId = cfg.getOrCreateIntProperty("daggerSacrifice",
					Configuration.CATEGORY_ITEM, daggerSacrificeId).getInt();
			daggerRitualId = cfg.getOrCreateIntProperty("daggerRitual",
					Configuration.CATEGORY_ITEM, daggerRitualId).getInt();
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
