package tco.daemon;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntityEnderChest;

public class TileDaemonEnderChest extends TileEntityEnderChest {
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}
	@Override
	public void openChest() {}
	@Override
	public void closeChest() {}
}
