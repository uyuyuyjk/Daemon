package tco.daemon.machines;

import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraftforge.common.ForgeDirection;

public class ContainerDecomposer extends ContainerDaemon {

	public ContainerDecomposer(InventoryPlayer inventoryPlayer,
			TileEntityDecomposer tileEntity) {
		super(tileEntity);

		addSlotToContainer(new Slot(tileEntity, tileEntity.getStartInventorySide(ForgeDirection.UP), 77, 38));
		addSlotToContainer(new Slot(tileEntity, tileEntity.getStartInventorySide(ForgeDirection.EAST), 127, 38));

		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			if (slot < 2) {
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
				if (!mergeItemStack(stackInSlot, 2,
						inventorySlots.size(), true)) {
					return null;
				}
			} else if (!mergeItemStack(stackInSlot, 0, 1,
					false)) {
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
}
