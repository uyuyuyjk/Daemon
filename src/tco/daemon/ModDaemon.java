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
package tco.daemon;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.CommandHandler;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumRarity;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemReed;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import tco.daemon.energy.DaemonEnergy;
import tco.daemon.energy.DecomposerRecipes;
import tco.daemon.handlers.CraftingHandler;
import tco.daemon.handlers.EventHandler;
import tco.daemon.handlers.GuiHandler;
import tco.daemon.handlers.PacketHandler;
import tco.daemon.machines.BlockBrazier;
import tco.daemon.machines.BlockDaemonMachine;
import tco.daemon.machines.ItemBlockDaemonMachine;
import tco.daemon.util.ReferenceConfigs;
import tco.daemon.util.ReferenceTiles;
import tco.daemon.util.UtilItem;
import tco.daemon.world.WorldGeneratorDaemonOres;
import tco.daemon.world.WorldProviderDaemon;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod( modid = "ModDaemon", name="Daemon", version=ReferenceConfigs.VERSION)
@NetworkMod(channels = { ReferenceConfigs.CHANNEL },
clientSideRequired = true,	serverSideRequired = false, packetHandler = PacketHandler.class)
public class ModDaemon {
	@Instance
	public static ModDaemon instance;
	@SidedProxy(clientSide = "tco.daemon.client.ProxyClient", serverSide = "tco.daemon.ProxyCommon")
	public static ProxyCommon proxy;

	public int dimensionId = 2;
	//blocks
	public Block blockCursedStone, blockCrystalOre;
	public Block blockDaemon, blockAltar, blockBrazier;

	//misc.
	public Item daemonBrazier;

	public Item matrixContained;
	public Item twistedSeed;
	public Item birdCannnon;
	public Item arrowUnstable;

	//daggers
	public Item daggerSacrifice, daggerSouls, daggerRitual;

	//amulets
	public Item amuletFire, amuletBlaze, amuletInferno;
	public Item amuletUnlife;

	//staves
	public Item staff;

	//shards
	public Item shardGlass, shardDark, shardUnstable, shardStable;

	//crystals
	public Item crystal;

	//orbs
	public Item orbMold,
	orbGlass,
	orbObsidian,
	orbBlaze,
	orbWolf,
	orbUnstable,
	orbStable;

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

		WorldGeneratorDaemonOres worldGenerator = new WorldGeneratorDaemonOres();
		GameRegistry.registerWorldGenerator(worldGenerator);

		registerDimension();

		GameRegistry.registerCraftingHandler(new CraftingHandler());
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		MinecraftForge.EVENT_BUS.register(new EventHandler());

		proxy.registerRenderInformation();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {

	}

	@ServerStarting
	public void serverStart(FMLServerStartingEvent event) {
		((CommandHandler) event.getServer().getCommandManager())
		.registerCommand(new CommandDaemon());
	}

	private void registerDimension() {
		while(!DimensionManager.registerProviderType(dimensionId, WorldProviderDaemon.class, false)) {
			dimensionId++;
		}
		DimensionManager.registerDimension(dimensionId, dimensionId);
	}

	private void loadBlocks(){
		blockCursedStone = new BlockCursedOre(ReferenceConfigs.blockCursedOre)
		.setBlockName("cursedOre");
		blockCursedStone.blockIndexInTexture = 16;
		GameRegistry.registerBlock(blockCursedStone);

		blockCrystalOre = new BlockCrystal(ReferenceConfigs.blockCrystalOre)
		.setBlockName("crystalOre");
		blockCrystalOre.blockIndexInTexture = 16 + 1;
		GameRegistry.registerBlock(blockCrystalOre);

		blockDaemon = new BlockDaemonMachine(ReferenceConfigs.blockDaemon)
		.setBlockName("blockDaemon");
		GameRegistry.registerBlock(blockDaemon, ItemBlockDaemonMachine.class);

		blockAltar = new BlockDaemonAltar(ReferenceConfigs.blockAltar)
		.setBlockName("blockAltar");
		GameRegistry.registerBlock(blockAltar);
		GameRegistry.registerTileEntity(TileEntityDaemonAltar.class, "daemonAltar");

		blockBrazier = new BlockBrazier(ReferenceConfigs.blockBrazier).setBlockName("blockDaemonBrazier");
		GameRegistry.registerBlock(blockBrazier);

		ReferenceTiles.registerTileEntities();
	}

	private void loadItems(){
		//blocks
		daemonBrazier = new ItemReed(ReferenceConfigs.daemonBrazier, blockBrazier).setItemName("daemonBrazier")
				.setTabToDisplayOn(CreativeTabs.tabMisc);
		daemonBrazier.setTextureFile(ReferenceConfigs.TEXTURE_ITEMS);

		//misc.
		matrixContained = new ItemMatrixContained(ReferenceConfigs.matrixContained)
		.setItemName("matrixContained");
		twistedSeed = new ItemTwistedSeed(ReferenceConfigs.twistedSeed)
		.setItemName("twistedSeed");
		birdCannnon = new ItemBirdCannon(ReferenceConfigs.birdCannnon)
		.setIconCoord(0, 4).setItemName("birdCannon");
		arrowUnstable = new ItemArrowUnstable(ReferenceConfigs.arrowUnstable)
		.setIconCoord(1, 4).setItemName("arrowUnstable");

		//daggers
		daggerSacrifice = new ItemDagger(ReferenceConfigs.daggerSacrifice, EnumToolMaterial.STONE)
		.setIconCoord(0, 0).setItemName("daggerSacrifice");
		daggerSouls = new ItemDagger(ReferenceConfigs.daggerSouls, EnumToolMaterial.IRON)
		.setRarity(EnumRarity.rare).setMaxDamage(0).setIconCoord(1, 0).setItemName("daggerSouls");
		daggerRitual = new ItemDaggerRitual(ReferenceConfigs.daggerRitual, EnumToolMaterial.EMERALD)
		.setRarity(EnumRarity.rare).setIconCoord(2, 0).setItemName("daggerRitual");

		//amulets
		amuletFire = new ItemAmuletFire(ReferenceConfigs.amuletFire)
		.setIconCoord(1, 2).setItemName("amuletFire");
		amuletBlaze = new ItemAmuletBlaze(ReferenceConfigs.amuletBlaze).setRarity(EnumRarity.uncommon)
				.setIconCoord(1, 2).setItemName("amuletBlaze");
		amuletInferno = new ItemAmuletBlaze(ReferenceConfigs.amuletInferno).setRarity(EnumRarity.epic)
				.setIconCoord(2, 2).setItemName("amuletInferno"); //TODO implement
		amuletUnlife = new ItemDaemon(ReferenceConfigs.amuletUnlife).setRarity(EnumRarity.epic)
				.setMaxStackSize(1).setIconCoord(0, 2).setItemName("amuletUnlife");

		//staves
		staff = new ItemStaff(ReferenceConfigs.staff, EnumToolMaterial.WOOD)
		.setIconCoord(3, 0).setItemName("staff");

		//shards
		shardGlass = new ItemShardGlass(ReferenceConfigs.shardGlass)
		.setIconCoord(6, 1);
		shardDark = new ItemShard(ReferenceConfigs.shardDark)
		.setIconCoord(7, 1).setItemName("shardDark");
		shardUnstable = new ItemShard(ReferenceConfigs.shardUnstable)
		.setIconCoord(7, 1).setItemName("shardUnstable");
		shardStable = new ItemShard(ReferenceConfigs.shardStable)
		.setIconCoord(7, 1).setItemName("shardStable");

		//crystals
		crystal = new ItemCrystal(ReferenceConfigs.crystal)
		.setIconCoord(0, 3).setItemName("crystal");

		//orbs
		orbMold = new ItemOrbMold(ReferenceConfigs.orbMold)
		.setIconCoord(5, 1).setItemName("orbMold");
		orbMold.setContainerItem(orbMold);
		orbGlass = new ItemOrb(ReferenceConfigs.orbGlass, 500)
		.setIconCoord(0, 1).setItemName("orbGlass");
		orbObsidian = new ItemOrb(ReferenceConfigs.orbObsidian, 2000).setRarity(EnumRarity.uncommon)
				.setIconCoord(1, 1).setItemName("orbObsidian");
		orbBlaze = new ItemOrb(ReferenceConfigs.orbBlaze, 10000).setRarity(EnumRarity.rare)
				.setIconCoord(2, 1).setItemName("orbBlaze");
		orbWolf = new ItemOrbWolf(ReferenceConfigs.orbWolf).setRarity(EnumRarity.rare)
				.setIconCoord(3, 1).setItemName("orbWolf");
		orbUnstable = new ItemOrbUnstable(ReferenceConfigs.orbUnstable).setRarity(EnumRarity.rare)
				.setIconCoord(4, 1).setItemName("orbUnstable");
		orbStable = new ItemOrbStable(ReferenceConfigs.orbStable).setRarity(EnumRarity.rare)
				.setIconCoord(4, 1).setItemName("orbStable");
	}

	private void registerEntities(){
		int chickenId = 1, wolfId = 2, arrowId = 3, gateId = 4;
		EntityRegistry.registerModEntity(EntityChickenDaemon.class, "CreeperChicken" , chickenId, this, 32, 5, true);
		EntityRegistry.registerModEntity(EntityWolfCreation.class, "Spirit Wolf" , wolfId, this, 32, 5, true);
		EntityRegistry.registerModEntity(EntityArrowUnstable.class, "Unstable Arrow" , arrowId, this, 32, 5, true);
		EntityRegistry.registerModEntity(EntityGateway.class, "Gateway" , gateId, this, 32, 5, true);
	}

	private void addRecipes(){
		CraftingManager cm = CraftingManager.getInstance();

		cm.addRecipe(new ItemStack(staff), new Object[]{"x", "x" , "x", 'x', Item.stick});
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

		//smelting
		GameRegistry.addSmelting(crystal.shiftedIndex, new ItemStack(shardGlass), 0);

		//low-efficiency version
		GameRegistry.addSmelting(blockCrystalOre.blockID, new ItemStack(crystal), 0);
		GameRegistry.addSmelting(blockCursedStone.blockID, new ItemStack(shardDark), 0);

		//decomposer
		DecomposerRecipes.addRecipe(blockCrystalOre.blockID,
				new DecomposerRecipes.DecomposerRecipe(50, new ItemStack(crystal)) {
			@Override
			public void handleCraft(ItemStack stack) {
				Random rand = new Random();
				DaemonEnergy de = UtilItem.getDaemonEnergy(stack);
				de.deathEnergy = rand.nextInt(255);
				de.decayEnergy = rand.nextInt(255);
				de.diseaseEnergy = rand.nextInt(255);
				de.maxEnergy = de.deathEnergy + de.decayEnergy + de.diseaseEnergy;
				UtilItem.setDaemonEnergy(stack, de);
			}
		});
		DecomposerRecipes.addRecipe(blockCursedStone.blockID, 50, new ItemStack(shardDark, 2));
	}

}
