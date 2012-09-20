package tco.daemon.machines;

import net.minecraft.src.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import tco.daemon.ItemCrystal;
import tco.daemon.ItemOrb;
import tco.daemon.ItemShardGlass;
import tco.daemon.util.DaemonEnergy;
import tco.daemon.util.IMatrixAction;
import tco.daemon.util.UtilItem;

public class MatrixActionConduct implements IMatrixAction {
	@Override
	public void doAction(ISidedInventory matrix, DaemonEnergy energy) {
		int size = matrix.getSizeInventorySide(ForgeDirection.DOWN);
		int orb = -1;
		ItemOrb orbItem = null;
		int lastSlot = 0;

		//find 1st orb
		for(; lastSlot < size; lastSlot++){
			ItemStack stack = matrix.getStackInSlot(lastSlot);
			if(stack != null && stack.getItem() instanceof ItemOrb){
				orb = lastSlot;
				orbItem = (ItemOrb) stack.getItem();
				break;
			}
		}
		if(orb < 0) return;

		//charge with glass shard if possible
		for(int i = 0; i < size; i++){
			ItemStack stack = matrix.getStackInSlot(i);
			if(stack != null
					&& stack.getItem() instanceof ItemShardGlass
					&& stack.getItemDamage() >= ItemShardGlass.DAMAGE_CHARGED){
				matrix.setInventorySlotContents(i, null);
				orbItem.chargeOrb(matrix.getStackInSlot(orb));
				return;
			}
		}

		//charge with crystal
		for(int i = 0; i < size; i++){
			ItemStack stack = matrix.getStackInSlot(i);
			if(stack != null && stack.getItem() instanceof ItemCrystal){
				matrix.setInventorySlotContents(i, null);
				DaemonEnergy de = UtilItem.getDaemonEnergy(matrix.getStackInSlot(orb));
				DaemonEnergy de2 = UtilItem.getDaemonEnergy(stack);
				de.merge(de2);
				UtilItem.setDaemonEnergy(matrix.getStackInSlot(orb), de);
				return;
			}
		}

		for(lastSlot++; lastSlot < size; lastSlot++){
			ItemStack stack = matrix.getStackInSlot(lastSlot);
			if(stack != null && stack.getItem() instanceof ItemOrb){
				matrix.setInventorySlotContents(orb, orbItem.mergeOrbs(matrix.getStackInSlot(orb), stack));
				matrix.setInventorySlotContents(lastSlot, null);
				return;
			}
		}
	}
}
