package tco.daemon;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntityEnderChest;

public class TileDaemonEnderChest extends TileEntityEnderChest {
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}
    public void openChest() {}
    public void closeChest() {}
}
