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

	public static final String VERSION = "0.1", REVISION = "3",	RELEASE_TYPE = "a";
	public static final String FULL_VERSION = "ModDaemon v" + VERSION + "." + REVISION + RELEASE_TYPE;
	public static final String CHANNEL = "ModDaemon";

	public static final String SPRITE_FOLDER = "/tco/daemon/sprites/";
	public static final String TEXTURE_BLOCKS = SPRITE_FOLDER + "blocks.png",
			TEXTURE_ITEMS = SPRITE_FOLDER + "daemonitems.png",
			TEXTURE_GATEWAY = SPRITE_FOLDER + "gateway.png",
			GUI_MATRIX = SPRITE_FOLDER + "matrix.png",
			GUI_FEEDER = SPRITE_FOLDER + "feeder.png",
			GUI_HUNGER_CHEST = "/gui/container.png",
			GUI_DECOMPOSER = SPRITE_FOLDER + "decomposer.png";

	public static final int DEATH_ENERGY_BLAZE = 50;
	public static final int DEATH_ENERGY_FIRE = 10;
	public static final int ENERGY_UNDEATH = 10000;

	//blocks
	public static @ConfigId(block=true) int blockCursedOre = 141,
			blockCrystalOre = 142,
			blockDaemon = 143,
			blockBrazier = 144,
			blockAltar = 145;//TODO use

	//misc.
	public static @ConfigId int daemonBrazier = 5432;

	public static @ConfigId int matrixContained = 5700, birdCannnon = 5436;

	public static @ConfigId int arrowUnstable = 5443;

	//daggers
	public static @ConfigId int daggerSacrifice = 5433,
			daggerSouls = 5437,
			daggerRitual = 5435;

	//amulets
	public static @ConfigId int amuletFire = 6938,
			amuletBlaze = 6939,
			amuletInferno = 6990, //TODO use
			amuletUnlife = 5437;

	//staves
	public static @ConfigId int staff = 5438,
			staffUndeath = 5439;//TODO use

	//shards
	public static @ConfigId int shardGlass = 5632,
			shardDark = 5631,
			shardUnstable = 5630,
			shardStable = 5629;

	//crystals
	public static @ConfigId int crystal = 5900;

	//orbs
	public static @ConfigId int orbMold = 5633,
			orbGlass = 5634,
			orbObsidian = 5635,
			orbBlaze = 5636,
			orbFamiliar = 5639,//TODO use
			orbWolf = 5637,
			orbUnstable = 5638;

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

	public static String getConfigString() {
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
