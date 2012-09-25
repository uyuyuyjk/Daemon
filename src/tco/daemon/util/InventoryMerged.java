package tco.daemon.util;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

public class InventoryMerged implements IInventory {

	private IInventory inventory1, inventory2;
	private int size1, size2;

	public InventoryMerged(IInventory inv1, IInventory inv2) {
		inventory1 = inv1;
		inventory2 = inv2;
		size1 = inventory1.getSizeInventory();
		size2 = inventory2.getSizeInventory();
	}

	@Override
	public int getSizeInventory() {
		return size1 + size2;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if(slot >= size1) {
			return inventory2.getStackInSlot(slot - size1);
		}
		return inventory1.getStackInSlot(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int stack) {
		if(slot >= size1) {
			return inventory2.decrStackSize(slot - size1, stack);
		}
		return inventory1.decrStackSize(slot, stack);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if(slot >= size1) {
			return inventory2.getStackInSlotOnClosing(slot - size1);
		}
		return inventory1.getStackInSlotOnClosing(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if(slot >= size1) {
			inventory2.setInventorySlotContents(slot - size1, stack);
		} else {
			inventory1.setInventorySlotContents(slot, stack);
		}
	}

	@Override
	public String getInvName() {
		return inventory1.getInvName();
	}

	@Override
	public int getInventoryStackLimit() {
		return inventory1.getInventoryStackLimit();
	}

	@Override
	public void onInventoryChanged() {
		inventory1.onInventoryChanged();
		inventory2.onInventoryChanged();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return inventory1.isUseableByPlayer(player) && inventory2.isUseableByPlayer(player);
	}

	@Override
	public void openChest() {
		inventory1.openChest();
		inventory2.openChest();
	}

	@Override
	public void closeChest() {
		inventory1.closeChest();
		inventory2.closeChest();
	}

}
