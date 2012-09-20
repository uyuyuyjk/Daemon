package tco.daemon.machines;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;

public class ContainerMatrixPortable extends ContainerMatrixContained {
	public ContainerMatrixPortable(EntityPlayer player) {
		super(player);
	}

	public void onCraftGuiClosed(EntityPlayer player) {
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack != null) {
				player.dropPlayerItem(stack);
				inventory.setInventorySlotContents(i, null);
			}
		}
	}
}
