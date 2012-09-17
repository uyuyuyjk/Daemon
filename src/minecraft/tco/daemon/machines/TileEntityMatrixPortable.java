package tco.daemon.machines;

import net.minecraft.src.ItemStack;
import tco.daemon.util.DaemonMatrix;

public class TileEntityMatrixPortable extends TileEntityDaemon {
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		super.setInventorySlotContents(slot, stack);
		if(slot == DaemonMatrix.MATRIX_SIZE - 1){
			updateMatrix();
		}
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = super.decrStackSize(slot, amt);
		if(slot == DaemonMatrix.MATRIX_SIZE - 1){
			updateMatrix();
		}
		return stack;
	}
}
