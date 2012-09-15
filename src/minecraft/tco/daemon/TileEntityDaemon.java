package tco.daemon;

import net.minecraft.src.Container;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import tco.daemon.util.DaemonEnergy;
import tco.daemon.util.DaemonMatrix;
import tco.daemon.util.UtilItem;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityDaemon extends TileEntity implements ISidedInventory {

	public static class InventoryCraftingDaemon extends InventoryCrafting{
		TileEntityDaemon tileEntity;

		public InventoryCraftingDaemon(TileEntityDaemon te) {
			super(new Container(){
				@Override
				public boolean canInteractWith(EntityPlayer player) {
					return false;
				}}, 3, 3);
			tileEntity = te;
		}
		
		//0<i<9 gets the corresponding slot of the tileEntity
		public int getDaemonSlot(int craftingSlot){
			if(craftingSlot < 3) return craftingSlot;
			if(craftingSlot < 6) return craftingSlot + DaemonMatrix.MATRIX_DIM - 3;
			return craftingSlot + 2 * (DaemonMatrix.MATRIX_DIM - 3);
		}
		
		//inverse of getDaemonSlot, unpredictable with wrong inputs 
		public int getCraftingSlot(int daemonSlot){
			if(daemonSlot < 4) return daemonSlot;
			if(daemonSlot < 8) return daemonSlot - DaemonMatrix.MATRIX_DIM - 3;
			return daemonSlot - 2 * (DaemonMatrix.MATRIX_DIM - 3);
		}
		
		public void populateCrafting(){
			for (int i = 0; i < 9; i++) {
				ItemStack stack = tileEntity.getStackInSlot(getDaemonSlot(i));
				setInventorySlotContents(i, stack);
			}
		}

		//TODO keepRecipe does stuff
		public ItemStack craft(boolean keepRecipe) {
			ItemStack result = CraftingManager.getInstance().findMatchingRecipe(this);
			if(result != null){
				for(int i = 0; i < 9; i++){
					ItemStack mat = getStackInSlot(i);
					if (mat != null)
					{
						if (mat.getItem().hasContainerItem()){
							ItemStack container = mat.getItem().getContainerItemStack(mat);
							tileEntity.setInventorySlotContents(getCraftingSlot(i), container);
						}else{
							tileEntity.removeItems(mat.getItem(), 1);
						}
					}
				}
			}
			return result;		
		}
		public ItemStack craft(){
			return craft(false);
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
		if(worldObj.isRemote){return;}
		

		DaemonMatrix type = DaemonMatrix.getType(this);
		DaemonEnergy energy = DaemonMatrix.calculateEnergy(this);
		
		int instability = DaemonMatrix.calculateInstability(this);
		
		switch (type) {
		case CRAFT:
			matrixCraft();
			break;
		case SMELT:
			break;
		case CONDUCT:
			matrixConduct();
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
		InventoryCraftingDaemon craftingInv = new InventoryCraftingDaemon(this);
		craftingInv.populateCrafting();
		ItemStack result = CraftingManager.getInstance().findMatchingRecipe(craftingInv);
		
		//TODO better filling code, this doesnt take into account stacking same items
		int emptySlot = getPreferredEmptySlot(ForgeDirection.DOWN);
		if(emptySlot < 0) return;

		if(result != null){
			for(int i = 0; i < 9; i++){
				ItemStack mat = craftingInv.getStackInSlot(i);
				if (mat != null)
				{
					if (mat.getItem().hasContainerItem()){
						ItemStack container = mat.getItem().getContainerItemStack(mat);
						setInventorySlotContents(craftingInv.getDaemonSlot(i), container);
					}else{
						removeItems(mat.getItem(), 1);
					}
				}
			}
			GameRegistry.onItemCrafted(null, result, craftingInv);
			setInventorySlotContents(emptySlot, result);
		}
		
	}
	
	public void matrixConduct(){
		int size = getSizeInventorySide(ForgeDirection.DOWN);
		int orb = -1;
		ItemOrb orbItem = null;
		int lastSlot = 0;
		
		//find 1st orb
		for(; lastSlot < size; lastSlot++){
			ItemStack stack = getStackInSlot(lastSlot);
			if(stack != null && stack.getItem() instanceof ItemOrb){
				orb = lastSlot;
				orbItem = (ItemOrb) stack.getItem();
				break;
			}
		}
		if(orb < 0) return;

		//charge with glass shard if possible
		for(int i = 0; i < size; i++){
			ItemStack stack = getStackInSlot(i);
			if(stack != null
					&& stack.getItem() instanceof ItemShardGlass
					&& stack.getItemDamage() >= ItemShardGlass.DAMAGE_CHARGED){
				setInventorySlotContents(i, null);
				orbItem.chargeOrb(getStackInSlot(orb));
				return;
			}
		}
		
		//charge with crystal
		for(int i = 0; i < size; i++){
			ItemStack stack = getStackInSlot(i);
			if(stack != null && stack.getItem() instanceof ItemCrystal){
				setInventorySlotContents(i, null);
				DaemonEnergy de = UtilItem.getDaemonEnergy(getStackInSlot(orb));
				DaemonEnergy de2 = UtilItem.getDaemonEnergy(stack);
				de.merge(de2);
				UtilItem.setDaemonEnergy(getStackInSlot(orb), de);
				return;
			}
		}
		
		for(lastSlot++; lastSlot < size; lastSlot++){
			ItemStack stack = getStackInSlot(lastSlot);
			if(stack != null && stack.getItem() instanceof ItemOrb){
				setInventorySlotContents(orb, orbItem.mergeOrbs(getStackInSlot(orb), stack));
				setInventorySlotContents(lastSlot, null);
				return;
			}
		}
	}

	public void applyMatrixEnergy(DaemonEnergy energy, int instability){
		//overridden by inheriting classes
	}
	
	//assumes items exist
	//attempts to leave stacks with at least 1 item
	/**
	 * 
	 * @param side
	 * @param item
	 * @param amount amount of the item to remove from the inventory
	 */
	public void removeItems(ForgeDirection side, Item item, int amount){
		int size = getSizeInventorySide(side);
		
		for(int i = 0; i < size && amount > 0; i++){
			ItemStack stack = getStackInSlot(i);
			if(stack != null && stack.getItem() == item){
				int amt = stack.stackSize - 1; //maximum amt it can take from the stack
				if(amount < amt){
					amt = amount;
				}
				if(amt > 0){
					amount -= decrStackSize(i, amt).stackSize;
				}
			}
		}
		for (int i = 0; i < size && amount > 0; i++) {
			ItemStack stack = getStackInSlot(i);
			if (stack != null && stack.getItem() == item) {
				amount -= decrStackSize(i, amount).stackSize;
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
	//TODO make it better
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
