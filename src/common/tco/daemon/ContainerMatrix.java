package tco.daemon;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraftforge.common.ForgeDirection;

public class ContainerMatrix extends Container {

	private TileEntityDaemon tileEntity;

	public ContainerMatrix(InventoryPlayer inventoryPlayer,
			TileEntityDaemon tileEntity2) {
		tileEntity = tileEntity2;
		
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				addSlotToContainer(new Slot(tileEntity, 4*i+j, 71 + 16 * j + 3*j, 7 + 16 * i+3*i));
			}
		}

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
				if (!mergeItemStack(stackInSlot, size,
						inventorySlots.size(), true)) {
					return null;
				}
			} else if (!mergeItemStack(stackInSlot, 0, size,
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
