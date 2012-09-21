package tco.daemon.handlers;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import tco.daemon.client.GuiMatrixContained;
import tco.daemon.client.GuiMatrixPortable;
import tco.daemon.machines.ContainerMatrixContained;
import tco.daemon.util.ReferenceTiles;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		if(id < 100){
			return ReferenceTiles.getContainer(id, player, world, x, y, z);
		}
		switch (id) {
		case ReferenceTiles.CONTAINED_MATRIX:
			return new ContainerMatrixContained(player);
		case ReferenceTiles.PORTABLE_MATRIX:
			return new ContainerMatrixContained(player);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		if(id < 100){
			return ReferenceTiles.getGui(id, player, world, x, y, z);
		}
		switch (id) {
		case ReferenceTiles.CONTAINED_MATRIX:
			return new GuiMatrixContained(player);
		case ReferenceTiles.PORTABLE_MATRIX:
			return new GuiMatrixPortable(player);
		}
		return null;
	}
}
