package tco.daemon;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import tco.daemon.client.GuiFeeder;
import tco.daemon.client.GuiHungerChest;
import tco.daemon.client.GuiMatrix;
import cpw.mods.fml.common.network.IGuiHandler;

public class ProxyCommon implements IGuiHandler {
	public void registerRenderInformation() {}
	
	
	public void openGui(int guiid, EntityPlayer player, World world, int x, int y, int z){
		player.openGui(ModDaemon.instance, guiid, world, x, y, z);
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityDaemon){
			switch(id){
				case ReferenceGui.MATRIX:
					return new ContainerMatrix(player.inventory, (TileEntityDaemon) tileEntity);
				case ReferenceGui.FEEDER:
					return new ContainerFeeder(player.inventory, (TileEntityFeeder) tileEntity);
				case ReferenceGui.CHEST:
					return new ContainerHungerChest(player.inventory, (TileEntityHungerChest) tileEntity);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityDaemon){
			switch(id){
			case ReferenceGui.MATRIX:
				return new GuiMatrix(player.inventory, (TileEntityDaemon) tileEntity);
			case ReferenceGui.FEEDER:
				return new GuiFeeder(player.inventory, (TileEntityFeeder) tileEntity);
			case ReferenceGui.CHEST:
				return new GuiHungerChest(player.inventory, (TileEntityHungerChest) tileEntity);
			}
		}
		return null;
	}
}
