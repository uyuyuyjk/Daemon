package tco.daemon;

import net.minecraft.src.ItemStack;

public class ItemOrbMold extends ItemDaemon {

	protected ItemOrbMold(int id) {
		super(id);
	}
	
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack){
		return false;
	}

}
