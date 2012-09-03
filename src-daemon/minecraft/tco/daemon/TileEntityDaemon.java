package tco.daemon;

import net.minecraft.src.Container;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityDaemon extends TileEntity implements IInventory, ISidedInventory {

	public static class InventoryCraftingDaemon extends InventoryCrafting{

		public InventoryCraftingDaemon() {
			super(new Container(){
				@Override
				public boolean canInteractWith(EntityPlayer var1) {
					return false;
				}}, 3, 3);
		}
		
	}
	
	protected ItemStack[] matrix;
			
	protected ItemStack[] inv;
	
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
		ItemStack stack = getStackInSlot(DaemonMatrix.MATRIX_SIZE - 1);

		DaemonMatrix type = DaemonMatrix.getType(stack);
		DaemonEnergy energy = DaemonMatrix.calculateEnergy(this);
		
		int instability = DaemonMatrix.calculateInstability(this);
		
		switch (type) {
		case CRAFT:
			matrixCraft();
			break;
		case SMELT:
			break;
		case CONDUCT:
			break;
		case CRAFTMATRIX:
			break;
		default:
			break;
		}
		applyMatrixEnergy(energy, instability);
	}
	
	//assumes 4x4
	public void matrixCraft(){
		int emptySlot = getPreferredEmptySlot(ForgeDirection.DOWN);
		if(emptySlot < 0) return;
		
		InventoryCraftingDaemon craftingInv = new InventoryCraftingDaemon();
		for (int mat = 0, i = 0; i < 9; i++, mat++) {
			if(mat == 3 || mat == 7) mat++;
			ItemStack stack = getStackInSlot(mat);
			craftingInv.setInventorySlotContents(i, stack);
		}
		
		ItemStack result = CraftingManager.getInstance().findMatchingRecipe(craftingInv);
		if(result != null){
			for(int i = 0; i < 9; i++){
				ItemStack mat = craftingInv.getStackInSlot(i);
				if(mat != null){
					removeItems(mat.getItem(), 1);
				}
			}
			//TODO better filling code, this doesnt take into account stacking same items
			setInventorySlotContents(emptySlot, result);
		}
		
	}

	public void applyMatrixEnergy(DaemonEnergy energy, int instability){
		
	}
	
	//assumes items exist
	//removes all but 1 or amount, depending on which comes first
	public void removeItems(ForgeDirection side, Item item, int amount){
		int start = getStartInventorySide(side);
		int size = getSizeInventorySide(side);
		
		for(int i = 0; i < size && amount > 0; i++){
			ItemStack stack = getStackInSlot(start + i);
			if(stack != null && stack.getItem() == item){
				int amt = stack.stackSize - 1;
				if(amount < amt){
					amt = amount;
				}
				amount -= decrStackSize(start + i, amt).stackSize;
			}
		}
	}
	
	public void removeItems(Item item, int amount){
		removeItems(ForgeDirection.DOWN, item, amount);
	}
	
	public void addItemToMatrix(){
		
	}
	
	//returns the sides, corners, then the main 3x3, or -1 is no empty slots found
	//assumes 4x4 matrix
	public int getPreferredEmptySlot(ForgeDirection side){
		int start = getStartInventorySide(side);
		int size = getSizeInventorySide(side);
		ItemStack stack = getStackInSlot(7);
		if(stack == null) return 7;
		stack = getStackInSlot(11);
		if(stack == null) return 11;
		stack = getStackInSlot(13);
		if(stack == null) return 13;
		stack = getStackInSlot(14);
		if(stack == null) return 14;
		
		stack = getStackInSlot(3);
		if(stack == null) return 3;
		stack = getStackInSlot(12);
		if(stack == null) return 12;
		stack = getStackInSlot(15);
		if(stack == null) return 15;

		for(int i = 0; i < size; i++){
			stack = getStackInSlot(start + i);
			if(stack == null) return i;
		}
		
		return -1;
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
		return "container.matrix";
	}

}
