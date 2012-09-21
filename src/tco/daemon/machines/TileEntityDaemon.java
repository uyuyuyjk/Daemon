package tco.daemon.machines;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import tco.daemon.energy.DaemonEnergy;
import tco.daemon.matrix.DaemonMatrix;
import tco.daemon.matrix.IMatrixAction;
import tco.daemon.matrix.IMatrixActivator;
import tco.daemon.util.UtilItem;

public class TileEntityDaemon extends TileEntity implements ISidedInventory {

	protected ItemStack[] matrix;
	protected ItemStack[] inv;

	protected int activatorId;
	protected IMatrixAction action;

	private int ticksSinceLastCalc;

	public TileEntityDaemon(){
		matrix = new ItemStack[DaemonMatrix.MATRIX_SIZE];
		inv = new ItemStack[0];
	}

	@Override
	public void updateEntity() {
		ticksSinceLastCalc = (ticksSinceLastCalc + 1) % 20;
		if(ticksSinceLastCalc == 0 && !worldObj.isRemote){
			updateMatrix();
		}
	}

	public void updateMatrix(){
		if(worldObj.isRemote){return;}

		ItemStack storage = DaemonMatrix.getStorageItem(this);
		DaemonEnergy storageEnergy = null;
		if(storage != null) {
			storageEnergy = UtilItem.getDaemonEnergy(storage);
		}


		IMatrixActivator activator = DaemonMatrix.getMatrixActivator(this);
		if(activator != null) {
			int id = activator.getActivatorId();
			if(id == activatorId) {
				action.doAction(this, storageEnergy);
			} else {
				activatorId = id;
				action = activator.getAction();
			}
		} else {
			activatorId = 0;
			action = null;
			defaultMatrixAction(storageEnergy);
		}


		if(storage != null) {
			UtilItem.setDaemonEnergy(storage, storageEnergy);
		}
	}

	public void defaultMatrixAction(DaemonEnergy storageEnergy) {
		DaemonEnergy energy = DaemonMatrix.calculateEnergy(this);
		int instability = DaemonMatrix.calculateInstability(this);
	}

	@Override
	public int getSizeInventory() {
		return matrix.length + inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if(slot < matrix.length){
			return matrix[slot];
		}
		return inv[slot - matrix.length];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if(slot < matrix.length){
			matrix[slot] = stack;
		}else{
			inv[slot - matrix.length] = stack;
		}
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	//3 methods to make accessing normal inventory easier

	public int getSize(){
		return inv.length;
	}

	public ItemStack getStack(int slot){
		return inv[slot];
	}

	public void setStack(int slot, ItemStack stack){
		inv[slot] = stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
				player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("SpecialInventory");
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < matrix.length) {
				matrix[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}

		tagList = tagCompound.getTagList("Inventory");
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inv.length) {
				inv[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}

		//matrix action
		activatorId = tagCompound.getInteger("ActivatorId");
		if(tagCompound.hasKey("MatrixAction")) {
			Item activator = Item.itemsList[activatorId];
			if(activator instanceof IMatrixActivator && tagCompound.hasKey("MatrixAction")) {
				NBTTagCompound actionTag = (NBTTagCompound) tagCompound.getTag("MatrixAction");
				action.readFromNBT(actionTag);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < matrix.length; i++) {
			ItemStack stack = matrix[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("SpecialInventory", itemList);

		itemList = new NBTTagList();
		for (int i = 0; i < inv.length; i++) {
			ItemStack stack = inv[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);

		//matrix action
		tagCompound.setInteger("ActivatorId", activatorId);
		if(action != null) {
			NBTTagCompound actionTag = new NBTTagCompound();
			action.writeToNBT(actionTag);
			tagCompound.setTag("MatrixAction", actionTag);
		}
	}

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		switch (side) {
		case DOWN:
			return 0;
		default:
			return matrix.length;
		}
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		switch (side) {
		case DOWN:
			return matrix.length;
		default:
			return inv.length;
		}
	}

	@Override
	public String getInvName() {
		return "matrix.name";
	}

}
