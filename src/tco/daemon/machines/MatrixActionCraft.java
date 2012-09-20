package tco.daemon.machines;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.src.Container;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import tco.daemon.util.DaemonEnergy;
import tco.daemon.util.DaemonMatrix;
import tco.daemon.util.IMatrixAction;
import tco.daemon.util.UtilItem;

public class MatrixActionCraft implements IMatrixAction {

	public static class InventoryCraftingDaemon extends InventoryCrafting {
		private ISidedInventory matrix;

		public InventoryCraftingDaemon(ISidedInventory m) {
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
	public void doAction(ISidedInventory matrix, DaemonEnergy energy) {		
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
	//returns the sides, corners, then the main 3x3, or -1 is no empty slots found
	//assumes 4x4 matrix
	//TODO make it better
	public int getPreferredEmptySlot(ISidedInventory matrix, ForgeDirection side){
		int start = matrix.getStartInventorySide(side);
		int size = matrix.getSizeInventorySide(side);
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

		for(int i = 0; i < size; i++){
			stack = matrix.getStackInSlot(start + i);
			if(stack == null) return i;
		}

		return -1;
	}
}
