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

import java.util.logging.Level;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumRarity;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemReed;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLLog;
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
	
	public Item birdCannnon;
	
	public Item staff;
	public Item daggerSacrifice, daggerSouls, daggerRitual;
	public Item arrowUnstable;

	public Item glassShard;
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
		
		GameRegistry.registerCraftingHandler(new CraftingHandlerDaemon());
		
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
		
		glassShard = new ItemGlassShard(ReferenceConfigs.glassShardId)
			.setIconCoord(6, 1);
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
		
		arrowUnstable = new ItemDaemon(ReferenceConfigs.arrowUnstableId)
			.setIconCoord(0, 1).setItemName("arrowUnstable");
				
		staff = new ItemStaff(ReferenceConfigs.staffId, EnumToolMaterial.WOOD)
			.setIconCoord(3, 0).setItemName("staff");
		
		daggerSacrifice = new ItemDagger(ReferenceConfigs.daggerSacrificeId, EnumToolMaterial.STONE)
			.setIconCoord(0, 0).setItemName("daggerSacrifice");
		daggerSouls = new ItemDagger(ReferenceConfigs.daggerSoulsId, EnumToolMaterial.IRON)
			.setRarity(EnumRarity.rare).setMaxDamage(0).setIconCoord(1, 0).setItemName("daggerSouls");
		daggerRitual = new ItemDaggerRitual(ReferenceConfigs.daggerRitualId, EnumToolMaterial.EMERALD)
			.setRarity(EnumRarity.rare).setIconCoord(2, 0).setItemName("daggerRitual");
		
		birdCannnon = new ItemBirdCannon(ReferenceConfigs.birdCannnonId)
			.setIconCoord(0, 4).setItemName("birdCannon");
	}
	
	private void registerEntities(){
		int chickenId = 1, wolfId = 2, arrowId = 3;
		EntityRegistry.registerModEntity(EntityChickenDaemon.class, "CreeperChicken" , chickenId, this, 32, 5, true);
		EntityRegistry.registerModEntity(EntityWolfCreation.class, "Spirit Wolf" , wolfId, this, 32, 5, true);
		EntityRegistry.registerModEntity(EntityUnstableArrow.class, "Unstable Arrow" , arrowId, this, 32, 5, true);
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
		
		GameRegistry.addSmelting(orbGlass.shiftedIndex, new ItemStack(glassShard, 1), 1);
	}

}
