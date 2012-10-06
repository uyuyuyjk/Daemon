package tco.daemon.util.matrix;

import net.minecraft.src.Container;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import tco.daemon.util.UtilItem;
import tco.daemon.util.energy.DaemonEnergy;
import cpw.mods.fml.common.registry.GameRegistry;

public class MatrixActionCraft implements IMatrixAction {

	public static class InventoryCraftingDaemon extends InventoryCrafting {
		private IInventory matrix;

		public InventoryCraftingDaemon(IInventory m) {
			super(new Container(){
				@Override
				public boolean canInteractWith(EntityPlayer player) {
					return false;
				}}, 3, 3);
			matrix = m;
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
				ItemStack stack = matrix.getStackInSlot(getDaemonSlot(i));
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
							matrix.setInventorySlotContents(getCraftingSlot(i), container);
						}else{
							UtilItem.removeItems(matrix, mat.getItem(), 1);
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

	//assumes 4x4
	@Override
	public void doAction(IInventory matrix, DaemonEnergy energy) {
		if(energy != null){
			doDaemonCraft(matrix, energy);
			return;
		}
		InventoryCraftingDaemon craftingInv = new InventoryCraftingDaemon(matrix);
		craftingInv.populateCrafting();
		ItemStack result = CraftingManager.getInstance().findMatchingRecipe(craftingInv);

		//TODO better filling code, this doesnt take into account stacking same items
		int emptySlot = getPreferredEmptySlot(matrix, ForgeDirection.DOWN);
		if(emptySlot < 0) return;

		if(result != null){
			for(int i = 0; i < 9; i++){
				ItemStack mat = craftingInv.getStackInSlot(i);
				if (mat != null)
				{
					if (mat.getItem().hasContainerItem()){
						ItemStack container = mat.getItem().getContainerItemStack(mat);
						matrix.setInventorySlotContents(craftingInv.getDaemonSlot(i), container);
					}else{
						UtilItem.removeItems(matrix, mat.getItem(), 1);
					}
				}
			}
			GameRegistry.onItemCrafted(null, result, craftingInv);
			matrix.setInventorySlotContents(emptySlot, result);
		}
	}

	private void doDaemonCraft(IInventory matrix, DaemonEnergy energy) {
		//TODO stub
	}

	//returns the sides, corners, then the main 3x3, or -1 is no empty slots found
	//assumes 4x4 matrix
	//TODO make it better
	public int getPreferredEmptySlot(IInventory matrix, ForgeDirection side){
		ItemStack stack = matrix.getStackInSlot(7);
		if(stack == null) return 7;
		stack = matrix.getStackInSlot(11);
		if(stack == null) return 11;
		stack = matrix.getStackInSlot(13);
		if(stack == null) return 13;
		stack = matrix.getStackInSlot(14);
		if(stack == null) return 14;

		stack = matrix.getStackInSlot(3);
		if(stack == null) return 3;
		stack = matrix.getStackInSlot(12);
		if(stack == null) return 12;
		stack = matrix.getStackInSlot(15);
		if(stack == null) return 15;

		for(int i = 0; i < DaemonMatrix.MATRIX_SIZE; i++){
			stack = matrix.getStackInSlot(i);
			if(stack == null) return i;
		}

		return -1;
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {}
}
