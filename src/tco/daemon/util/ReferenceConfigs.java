/******************************************
 * Copyright (c) 2012 tcooc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * The Software shall be used for Good, not Evil.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 ******************************************/
package tco.daemon.util;

import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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

	public static final String VERSION = "0.1.2a";
	public static final String FULL_VERSION = "ModDaemon v" + VERSION;
	public static final String CHANNEL = "ModDaemon";

	public static final String TEXTURE_BLOCKS = "/tco/daemon/sprites/blocks.png",
			TEXTURE_ITEMS = "/tco/daemon/sprites/daemonitems.png",
			TEXTURE_GATEWAY = "/tco/daemon/sprites/gateway.png",
			GUI_MATRIX = "/tco/daemon/sprites/matrix.png",
			GUI_FEEDER = "/tco/daemon/sprites/feeder.png",
			GUI_HUNGER_CHEST = "/gui/container.png",
			GUI_DECOMPOSER = "/tco/daemon/sprites/decomposer.png";

	public static @ConfigId(block=true) int blockCursedOreId = 141,
			blockCrystalOreId = 142,
			blockDaemonId = 143,
			blockBrazierId = 144,
			blockAltarId = 145;//TODO use

	public static @ConfigId int daemonBrazierId = 5432;

	public static @ConfigId int matrixContainedId = 5700;

	public static @ConfigId int daggerSacrificeId = 5433,
			daggerSoulsId = 5437,
			daggerRitualId = 5435,
			birdCannnonId = 5436,
			amuletUnlife = 5437;//TODO use

	public static @ConfigId int staffId = 5438,
			staffUndeathId = 5439;//TODO use

	public static @ConfigId int arrowUnstableId = 5443;

	public static @ConfigId int shardGlassId = 5632,
			shardDarkId = 5631,
			shardUnstableId = 5630,
			shardStableId = 5629;
	public static @ConfigId int crystalId = 5900;
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

	public static String getConfigs() {
		StringBuffer configs = new StringBuffer();
		Field[] fields = ReferenceConfigs.class.getFields();
		for(Field field : fields){
			ConfigId annotation = field.getAnnotation(ConfigId.class);
			if(annotation == null)
				continue;
			try{
				int id = field.getInt(null);
				configs.append(field.getName()).append('=').append(id).append(", ");
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return configs.toString();
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
