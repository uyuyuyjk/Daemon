package tco.daemon;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod( modid = "ModDaemon", name="Daemon", version="0.1")
@NetworkMod(channels = { "ModDaemon" },
	clientSideRequired = true,	serverSideRequired = false,
	packetHandler = PacketHandler.class)
public class ModDaemon {
	@Instance
	public static ModDaemon instance;
	@SidedProxy(clientSide = "tco.daemon.client.ProxyClient", serverSide = "tco.daemon.ProxyCommon")
	public static ProxyCommon proxy;
	
	//content
	public BlockDaemon blockDaemon;
	
	public Item daggerSacrifice;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		ReferenceConfigs.loadConfigs(event);
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
		blockDaemon = new BlockDaemon(ReferenceConfigs.daemonBlockId);
		GameRegistry.registerBlock(blockDaemon, ItemBlockDaemon.class);
		
		GameRegistry.registerTileEntity(TileEntityFeeder.class, "tileEntityFeeder");
		GameRegistry.registerTileEntity(TileEntityHungerChest.class, "tileEntityHungerChest");
	}
	
	private void loadItems(){
		//TODO serious localization
		LanguageRegistry.instance().addStringLocalization("container.matrix", "Matrix");
		LanguageRegistry.instance().addStringLocalization("container.feeder", "Feeder");
		LanguageRegistry.instance().addStringLocalization("container.hungerChest", "Hunger Chest");

		daggerSacrifice = new ItemDagger(ReferenceConfigs.daggerSacrificeId);
		LanguageRegistry.instance().addStringLocalization("item.daggerSacrifice.name", "Sacrificial Dagger");
		
		new ItemBirdCannon(23432);
		LanguageRegistry.instance().addStringLocalization("item.birdcannon.name", "Infernal Cannon");
	}
	
	private void registerEntities(){
		EntityRegistry.registerModEntity(EntityChickenDaemon.class, "Creeper Chicken" , EntityRegistry.findGlobalUniqueEntityId(), this, 16, 5, true);
	}
	
	private void addRecipes(){
		CraftingManager.getInstance().addRecipe(new ItemStack(blockDaemon, 1, 1), new Object[]{"x", 'x', Block.dirt});
		CraftingManager.getInstance().addRecipe(new ItemStack(blockDaemon, 1, 2), new Object[]{"xx", 'x', Block.dirt});
	}

}
