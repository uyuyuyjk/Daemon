package tco.daemon;

import net.minecraft.src.*;
import net.minecraftforge.common.ForgeDirection;

public class ContainerFeeder extends Container {

	private TileEntityFeeder tileEntity;

	public ContainerFeeder(InventoryPlayer inventoryPlayer,
			TileEntityFeeder tileEntity2) {
		tileEntity = tileEntity2;
		
		addSlotToContainer(new Slot(tileEntity, tileEntity.getStartInventorySide(ForgeDirection.UP), 76, 37));

		//Start base code for player inventory
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
		//End base code for player inventory
	}

	@Override
	public ItemStack transferStackInSlot(int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			if (slot == 0) {
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
				if (!mergeItemStack(stackInSlot, 1,
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

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}

}
