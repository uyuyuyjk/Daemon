package tco.daemon;

import net.minecraft.src.ItemStack;

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
