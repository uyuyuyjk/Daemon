package tco.daemon.item;

import net.minecraft.src.ItemStack;

public class ItemOrbMold extends ItemDaemon {

	public ItemOrbMold(int id) {
		super(id);
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack){
		return false;
	}

}
