package tco.daemon.machines;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import tco.daemon.matrix.DaemonMatrix;
import tco.daemon.util.UtilItem;

public class ContainerMatrixContained extends ContainerDaemon {

	IInventory inventory;

	public ContainerMatrixContained(EntityPlayer player) {
		super(null);
		inventory =
				UtilItem.getInventory(player, player.inventory.getCurrentItem(),
						player.inventory, DaemonMatrix.MATRIX_SIZE);
		for (int i = 0; i < DaemonMatrix.MATRIX_DIM; i++) {
			for (int j = 0; j < DaemonMatrix.MATRIX_DIM; j++) {
				addSlotToContainer(new Slot(inventory, 4 * i + j, 71 + 16 * j
						+ 3 * j, 7 + 16 * i + 3 * i));
			}
		}

		bindPlayerInventory(player.inventory);
	}

	@Override
	public ItemStack transferStackInSlot(int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			int size = inventory.getSizeInventory();

			if (slot < size) {
				/*
				 * mergeItemStack(ItemStack, int, int2, boolean)
				 * 
				 * @param ItemStack item to merge into inventory
				 * 
				 * @param int minimum slot to attempt fill
				 * 
				 * @param int2 maximum slot to attempt fill
				 * 
				 * @param boolean fill backwards
				 * 
				 * @return true if stacks merged successfully
				 */
				if (!mergeItemStack(stackInSlot, size, inventorySlots.size(),
						true)) {
					return null;
				}
			} else if (!mergeItemStack(stackInSlot, 0, size, false)) {
				return null;
			}

			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}
		}

		return stack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
