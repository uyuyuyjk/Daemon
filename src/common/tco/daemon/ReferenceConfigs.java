package tco.daemon;

import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ReferenceConfigs {
	
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface ConfigId {
		public boolean block() default false;
	}

	public static final String VERSION = "0.1";
	
	public static final String TEXTURE_BLOCKS = "/tco/daemon/sprites/blocks.png",
			TEXTURE_ITEMS = "/tco/daemon/sprites/daemonitems.png",
			GUI_MATRIX = "/tco/daemon/sprites/matrix.png",
			GUI_FEEDER = "/tco/daemon/sprites/feeder.png",
			GUI_HUNGER_CHEST = "/gui/container.png";
	
	public static @ConfigId(block=true) int blockDaemonId = 143,
			blockBrazierId = 144,
			blockAltarId = 145;//TODO use
	
	public static @ConfigId int daemonBrazierId = 5432;

	public static @ConfigId int daggerSacrificeId = 5433,
			daggerSoulsId = 5437,
			daggerRitualId = 5435,
			birdCannnonId = 5436,
			amuletUnlife = 5437;//TODO use
	
	public static @ConfigId int staffId = 5438,
			staffUndeathId = 5439;//TODO use
	
	public static @ConfigId int arrowUnstableId = 5443;

	public static @ConfigId int glassShardId = 5632;
	public static @ConfigId int orbMoldId = 5633,
			orbGlassId = 5634,
			orbObsidianId = 5635,
			orbBlazeId = 5636,
			orbFamiliarId = 5639,//TODO use
			orbWolfId = 5637,
			orbUnstableId = 5638;

	public static void loadConfigs(FMLPreInitializationEvent event) {
		Configuration cfg = new Configuration(
				event.getSuggestedConfigurationFile());
		try {
			cfg.load();
			
			Field[] fields = ReferenceConfigs.class.getFields();
			for(Field field : fields){
				ConfigId annotation = field.getAnnotation(ConfigId.class);
				if(annotation == null)
					continue;
				int id = field.getInt(null);
				if(annotation.block()){
					id = cfg.getOrCreateBlockIdProperty(field.getName(), id).getInt();
				}else{
					id = cfg.getOrCreateIntProperty(field.getName(), Configuration.CATEGORY_ITEM, id).getInt();
				}
				field.setInt(null, id);
			}
			
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
