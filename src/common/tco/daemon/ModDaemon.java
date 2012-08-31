package tco.daemon;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumRarity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemReed;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.MinecraftForge;
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

@Mod( modid = "ModDaemon", name="Daemon", version=ReferenceConfigs.VERSION)
@NetworkMod(channels = { "ModDaemon" },
	clientSideRequired = true,	serverSideRequired = false,
	packetHandler = PacketHandler.class)
public class ModDaemon {
	@Instance
	public static ModDaemon instance;
	@SidedProxy(clientSide = "tco.daemon.client.ProxyClient", serverSide = "tco.daemon.ProxyCommon")
	public static ProxyCommon proxy;
	
	//content
	public Block blockDaemon,
		blockBrazier;
	public Item daemonBrazier;
	
	public Item daggerSacrifice,
		birdCannnon;
	
	public Item daggerRitual;

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
		
		//GameRegistry.registerCraftingHandler(new MoldCraftingHandler());
		
		
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		MinecraftForge.EVENT_BUS.register(proxy);
		proxy.registerRenderInformation();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	private void loadBlocks(){
		blockDaemon = new BlockDaemon(ReferenceConfigs.blockDaemonId).setBlockName("blockDaemon");
		GameRegistry.registerBlock(blockDaemon, ItemBlockDaemon.class);
		
		blockBrazier = new BlockBrazier(ReferenceConfigs.blockBrazierId).setBlockName("blockDaemonBrazier");
		GameRegistry.registerBlock(blockBrazier);
		
		GameRegistry.registerTileEntity(TileEntityDaemon.class, "tileEntityDaemon");
		GameRegistry.registerTileEntity(TileEntityFeeder.class, "tileEntityFeeder");
		GameRegistry.registerTileEntity(TileEntityHungerChest.class, "tileEntityHungerChest");
		
	}
	
	private void loadItems(){
		daemonBrazier = new ItemReed(ReferenceConfigs.daemonBrazierId, blockBrazier).setItemName("daemonBrazier")
				.setTabToDisplayOn(CreativeTabs.tabMisc);
		daemonBrazier.setTextureFile(ReferenceConfigs.TEXTURE_ITEMS);
		
		orbMold = new ItemOrbMold(ReferenceConfigs.orbMoldId)
			.setIconCoord(5, 1).setItemName("orbMold");
			orbMold.setContainerItem(orbMold);
		orbGlass = new ItemOrb(ReferenceConfigs.orbGlassId, 50).setRarity(EnumRarity.uncommon)
			.setIconCoord(0, 1).setItemName("orbGlass");;
		orbObsidian = new ItemOrb(ReferenceConfigs.orbObsidianId, 200).setRarity(EnumRarity.rare)
			.setIconCoord(1, 1).setItemName("orbObsidian");
		orbBlaze = new ItemOrb(ReferenceConfigs.orbBlazeId, 1000).setRarity(EnumRarity.rare)
			.setIconCoord(2, 1).setItemName("orbBlaze");
		orbWolf = new ItemWolfOrb(ReferenceConfigs.orbWolfId).setRarity(EnumRarity.rare)
			.setIconCoord(3, 1).setItemName("orbWolf");
		orbUnstable = new ItemUnstableOrb(ReferenceConfigs.orbUnstableId).setRarity(EnumRarity.rare)
			.setIconCoord(4, 1).setItemName("orbUnstable");
		
		daggerSacrifice = new ItemDagger(ReferenceConfigs.daggerSacrificeId)
			.setIconCoord(0, 0).setItemName("daggerSacrifice");
		daggerRitual = new ItemDaggerRitual(ReferenceConfigs.daggerRitualId).setRarity(EnumRarity.rare)
			.setIconCoord(0, 0).setItemName("daggerRitual");
		
		birdCannnon = new ItemBirdCannon(ReferenceConfigs.birdCannnonId)
			.setIconCoord(1, 0).setItemName("birdCannon");
	}
	
	private void registerEntities(){
		int chickenId = EntityRegistry.findGlobalUniqueEntityId();
		int wolfId = 2;
		EntityRegistry.registerGlobalEntityID(EntityChickenDaemon.class, "CreeperChicken" , chickenId, 16, 5);
		EntityRegistry.registerModEntity(EntityWolfCreation.class, "Spirit Wolf" , wolfId, this, 200, 5, true);
	}
	
	private void addRecipes(){
		CraftingManager cm = CraftingManager.getInstance();
		//matrix recipes
		{
			Object[] matrixPattern = new Object[] { " R ", " C ", "XXX", 'C',
					Block.workbench, 'X', Block.stoneSingleSlab, 'R', null };
			Object[] matrixVariables = new Object[] { Item.rottenFlesh,
					Item.bone, Item.spiderEye };
			for (Object var : matrixVariables) {
				matrixPattern[8] = var;
				cm.addRecipe(new ItemStack(blockDaemon, 1, 0), matrixPattern);
			}
		}
		
		cm.addRecipe(new ItemStack(blockDaemon, 1, 1), new Object[]{"xx", 'x', Block.dirt});
		cm.addRecipe(new ItemStack(blockDaemon, 1, 2), new Object[]{"xxx", 'x', Block.dirt});
		
		cm.addRecipe(new ItemStack(orbMold), new Object[]{" X","XX", 'X', Item.ingotGold});
		
		//orb recipes
		{
			Object[] orbPattern = new Object[] { "MXM", "XOX", "MXM", 'M',
					orbMold, 'O', Item.enderPearl, 'X', null };
			ItemStack[] orbResults = new ItemStack[] { new ItemStack(orbGlass),
					new ItemStack(orbObsidian), new ItemStack(orbBlaze) };
			Object[] orbVariables = new Object[] { Block.glass, Block.obsidian,
					Item.blazePowder };
			for (int i = 0; i < orbResults.length; i++) {
				orbPattern[8] = orbVariables[i];
				cm.addRecipe(orbResults[i], orbPattern);
			}
		}
	}

}
