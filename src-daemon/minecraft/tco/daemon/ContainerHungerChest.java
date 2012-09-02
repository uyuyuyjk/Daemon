package tco.daemon;

import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraftforge.common.ForgeDirection;

public class ContainerHungerChest extends ContainerDaemon {

    private int inventoryRows = 3;
    
	public ContainerHungerChest(InventoryPlayer inventoryPlayer,
			TileEntityHungerChest tileEntity) {
		super(tileEntity);
		
		int start = tileEntity.getStartInventorySide(ForgeDirection.UP);
		
		for (int i = 0; i < inventoryRows; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(tileEntity, start + j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}

		bindPlayerInventory(inventoryPlayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			if (slot < inventoryRows * 9) {
				if (!mergeItemStack(stackInSlot, inventoryRows * 9,
						inventorySlots.size(), true)) {
					return null;
				}
			} else if (!mergeItemStack(stackInSlot, 0, inventoryRows * 9, false)) {
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
}
