package tco.daemon.util;

import java.util.logging.Level;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import tco.daemon.client.GuiDecomposer;
import tco.daemon.client.GuiFeeder;
import tco.daemon.client.GuiHungerChest;
import tco.daemon.client.GuiMatrix;
import tco.daemon.machines.ContainerDecomposer;
import tco.daemon.machines.ContainerFeeder;
import tco.daemon.machines.ContainerHungerChest;
import tco.daemon.machines.ContainerMatrix;
import tco.daemon.machines.TileEntityDaemon;
import tco.daemon.machines.TileEntityDecomposer;
import tco.daemon.machines.TileEntityFeeder;
import tco.daemon.machines.TileEntityHungerChest;
import cpw.mods.fml.common.FMLLog;

public enum ReferenceGui {
	MATRIX("matrix", TileEntityDaemon.class, ContainerMatrix.class, GuiMatrix.class),
	FEEDER("feeder", TileEntityFeeder.class, ContainerFeeder.class, GuiFeeder.class),
	CHEST("hungerChest", TileEntityHungerChest.class, ContainerHungerChest.class, GuiHungerChest.class),
	DECOMPOSER("decomposer", TileEntityDecomposer.class, ContainerDecomposer.class, GuiDecomposer.class);

	public static final int CONTAINED_MATRIX = 100;
	public static final int PORTABLE_MATRIX = 101;

	private String name;
	private final Class tileEntity;
	private final Class container;
	private final Class guiContainer;
	ReferenceGui(String n, Class te, Class cont, Class gc) {
		name = n;
		tileEntity = te;
		container = cont;
		guiContainer = gc;
	}


	public String getName(){
		return name;
	}

	public TileEntity getTileEntity() {
		TileEntity te = null;
		try {
			te = (TileEntity) tileEntity.newInstance();
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "Daemon mod failed to create a TileEntity.");
		}
		return te;
	}

	public static Container getContainer(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		Container container = null;
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityDaemon){
			try {
				container = (Container) ReferenceGui.values()[id].container
						.getConstructor(InventoryPlayer.class, ReferenceGui.values()[id].tileEntity)
						.newInstance(player.inventory, tileEntity);
			} catch (Exception e) {
				FMLLog.log(Level.SEVERE, e, "Daemon mod failed to create a Container.");
			}
		}
		return container;
	}

	public static GuiContainer getGui(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		GuiContainer gui = null;
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityDaemon){
			try {
				gui = (GuiContainer) ReferenceGui.values()[id].guiContainer
						.getConstructor(InventoryPlayer.class, ReferenceGui.values()[id].tileEntity)
						.newInstance(player.inventory, tileEntity);
			} catch (Exception e) {
				FMLLog.log(Level.SEVERE, e, "Daemon mod failed to create a Gui.");
			}
		}
		return gui;
	}

}
