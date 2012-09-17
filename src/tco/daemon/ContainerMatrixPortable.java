package tco.daemon;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraftforge.common.ForgeDirection;
import tco.daemon.machines.ContainerDaemon;
import tco.daemon.machines.TileEntityMatrixPortable;

public class ContainerMatrixPortable extends ContainerDaemon {

	public ContainerMatrixPortable(InventoryPlayer inventoryPlayer) {
		super(new TileEntityMatrixPortable());
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				addSlotToContainer(new Slot(tileEntity, 4 * i + j, 71 + 16 * j
						+ 3 * j, 7 + 16 * i + 3 * i));
			}
		}

		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public void onCraftGuiClosed(EntityPlayer player) {
		super.onCraftGuiClosed(player);
		if (player.worldObj.isRemote) return;
		for (int i = 0; i < tileEntity.getSizeInventory(); ++i) {
			ItemStack stack = tileEntity.getStackInSlotOnClosing(i);
			if (stack != null) player.dropPlayerItem(stack);
		}
	}

	@Override
	public ItemStack transferStackInSlot(int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			int size = tileEntity.getSizeInventorySide(ForgeDirection.DOWN);

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
