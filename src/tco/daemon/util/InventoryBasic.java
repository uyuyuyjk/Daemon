package tco.daemon.util;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;

public class InventoryBasic implements IInventory {
	
	private ItemStack[] inventory;
	private String name;
	
	public InventoryBasic(String name, int slots){
		this.name = name;
		inventory = new ItemStack[slots];
	}
	
	public InventoryBasic(String name, IInventory inv){
		this.name = name;
		inventory = new ItemStack[inv.getSizeInventory()];
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			setInventorySlotContents(i, inv.getStackInSlot(i));
		}
	}

	public void saveToNBT(NBTTagCompound tagCompound){
		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack stack = getStackInSlot(i);
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag(name, itemList);
	}
	
	public void getFromNBT(NBTTagCompound tagCompound){
		NBTTagList itemList = tagCompound.getTagList(name);
		for (int i = 0; i < itemList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) itemList.tagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < getSizeInventory()) {
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(tag));
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int amt) {
		ItemStack stack = getStackInSlot(i);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(i, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(i, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack stack = getStackInSlot(i);
		if (stack != null) {
			setInventorySlotContents(i, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		inventory[i] = stack;
	}

	@Override
	public String getInvName() {
		return "conainer." + name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() {}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

}
