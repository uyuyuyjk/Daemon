package tco.daemon;

import java.util.Properties;

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
	
	public Item daggerSacrifice,
		birdCannnon;

	public Item orbMold,
		orbGlass,
		orbObsidian,
		orbBlaze,
		orbWolf,
		orbUnstable;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		ReferenceConfigs.loadConfigs(event);
		ReferenceConfigs.loadLocalizationProps();
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
		
		GameRegistry.registerTileEntity(TileEntityDaemon.class, "tileEntityDaemon");
		GameRegistry.registerTileEntity(TileEntityFeeder.class, "tileEntityFeeder");
		GameRegistry.registerTileEntity(TileEntityHungerChest.class, "tileEntityHungerChest");
		
	}
	
	private void loadItems(){
		orbMold = new ItemDaemon(ReferenceConfigs.orbMoldId)
			.setIconCoord(5, 1).setItemName("orbMold");
			orbMold.setContainerItem(orbMold);
		orbGlass = new ItemDaemon(ReferenceConfigs.orbGlassId)
			.setIconCoord(0, 1).setItemName("orbGlass");;
		orbObsidian = new ItemDaemon(ReferenceConfigs.orbObsidianId)
			.setIconCoord(1, 1).setItemName("orbObsidian");
		orbBlaze = new ItemDaemon(ReferenceConfigs.orbBlazeId)
			.setIconCoord(2, 1).setItemName("orbBlaze");
		orbWolf = new ItemWolfOrb(ReferenceConfigs.orbWolfId)
			.setIconCoord(3, 1).setItemName("orbWolf");
		orbUnstable = new ItemUnstableOrb(ReferenceConfigs.orbUnstableId)
			.setIconCoord(4, 1).setItemName("orbUnstable");
		
		daggerSacrifice = new ItemDagger(ReferenceConfigs.daggerSacrificeId)
			.setIconCoord(0, 0).setItemName("daggerSacrifice");;
		
		birdCannnon = new ItemBirdCannon(ReferenceConfigs.birdCannnonId)
			.setIconCoord(1, 0).setItemName("birdCannon");
	}
	
	private void registerEntities(){
		EntityRegistry.registerModEntity(EntityChickenDaemon.class, "Creeper Chicken" , EntityRegistry.findGlobalUniqueEntityId(), this, 16, 5, true);
		EntityRegistry.registerModEntity(EntityWolfCreation.class, "Spirit Wolf" , EntityRegistry.findGlobalUniqueEntityId(), this, 255, 5, true);
	}
	
	private void addRecipes(){
		CraftingManager cm = CraftingManager.getInstance();
		cm.addRecipe(new ItemStack(blockDaemon, 1, 0), new Object[]{"x", 'x', Block.dirt});
		cm.addRecipe(new ItemStack(blockDaemon, 1, 1), new Object[]{"xx", 'x', Block.dirt});
		cm.addRecipe(new ItemStack(blockDaemon, 1, 2), new Object[]{"xxx", 'x', Block.dirt});
		
		cm.addRecipe(new ItemStack(orbMold), new Object[]{" X","XX", 'X', Item.ingotGold});

		Object[] orbPattern = new Object[]{"MXM","XOX","MXM", 'M', orbMold, 'O', Item.enderPearl, 'X', Block.glass};
		cm.addRecipe(new ItemStack(orbGlass), orbPattern);
		orbPattern[8] = Block.obsidian;
		cm.addRecipe(new ItemStack(orbObsidian), orbPattern);
		orbPattern[8] = Item.blazePowder;
		cm.addRecipe(new ItemStack(orbBlaze), orbPattern);
	}

}
