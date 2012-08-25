package tco.daemon;

import java.util.logging.Level;

import net.minecraft.src.*;
import net.minecraft.src.Block;
import net.minecraft.src.Item;

import net.minecraftforge.common.*;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.registry.*;

@Mod( modid = "ModDaemon", name="Daemon", version="0.1")
@NetworkMod(channels = { "ModDaemon" },
	clientSideRequired = true,	serverSideRequired = false,
	packetHandler = PacketHandler.class)
public class Daemon {
	@Instance
	public static Daemon instance;
	@SidedProxy(clientSide = "tco.daemon.ProxyClient", serverSide = "tco.daemon.ProxyCommon")
	public static ProxyCommon proxy;
	
	//configuration  options
	private int daemonBlockId = 143;
	private int daggerSacrificeId = 5433;

	//content
	public BlockDaemon blockDaemon;
	
	public Item daggerSacrifice;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try{
			cfg.load();
			daemonBlockId = cfg.getOrCreateBlockIdProperty("BlockDaemon", daemonBlockId).getInt(daemonBlockId);
			daggerSacrificeId = cfg.getOrCreateIntProperty("daggerSacrifice", Configuration.CATEGORY_ITEM, daggerSacrificeId).getInt(daggerSacrificeId);
		}catch(Exception e){
			FMLLog.log(Level.SEVERE, e, "Failed to load Daemon mod configs.");
		}finally{
			cfg.save();
		}
	}

	@Init
	public void load(FMLInitializationEvent event){
		loadBlocks();
		loadItems();
		registerEntities();
		addRecipes();
		
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		
		proxy.registerRenderInformation();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	private void loadBlocks(){
		blockDaemon = new BlockDaemon(daemonBlockId);
		GameRegistry.registerBlock(blockDaemon, ItemBlockDaemon.class);
		
		GameRegistry.registerTileEntity(TileEntityFeeder.class, "tileEntityFeeder");
		GameRegistry.registerTileEntity(TileEntityHungerChest.class, "tileEntityHungerChest");
	}
	
	private void loadItems(){
		LanguageRegistry.instance().addStringLocalization("feeder.name", "Feeder");
		LanguageRegistry.instance().addStringLocalization("hungerChest.name", "Hunger Chest");

		daggerSacrifice = new ItemDagger(daggerSacrificeId);
		LanguageRegistry.instance().addStringLocalization("item.daggerSacrifice.name", "Sacrificial Dagger");
		
		new ItemBirdCannon(23432);
	}
	
	private void registerEntities(){
		EntityRegistry.registerModEntity(EntityChickenDaemon.class, "Creeper Chicken" , EntityRegistry.findGlobalUniqueEntityId(), this, 16, 5, true);
	}
	
	private void addRecipes(){
		CraftingManager.getInstance().addRecipe(new ItemStack(blockDaemon, 1, 1), new Object[]{"x", 'x', Block.dirt});
		CraftingManager.getInstance().addRecipe(new ItemStack(blockDaemon, 1, 2), new Object[]{"xx", 'x', Block.dirt});
	}

}
