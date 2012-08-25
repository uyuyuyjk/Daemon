package tco.daemon;

import java.util.Random;

import net.minecraft.src.*;

import cpw.mods.fml.common.network.IGuiHandler;

public class ProxyCommon implements IGuiHandler {
	public void registerRenderInformation() {}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityDaemon){
			switch(id){
				case BlockDaemon.MATRIX:
					return new ContainerMatrix(player.inventory, (TileEntityDaemon) tileEntity);
				case BlockDaemon.FEEDER:
					return new ContainerFeeder(player.inventory, (TileEntityFeeder) tileEntity);
				case BlockDaemon.CHEST://TODO stub
					break;
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
			case BlockDaemon.MATRIX:
				return new GuiMatrix(player.inventory, (TileEntityDaemon) tileEntity);
			case BlockDaemon.FEEDER:
				return new GuiFeeder(player.inventory, (TileEntityFeeder) tileEntity);
			case BlockDaemon.CHEST://TODO stub
				break;
			}
		}
		return null;
	}
}
