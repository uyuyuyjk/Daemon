package tco.daemon;

import net.minecraft.src.ItemStack;

public class TileEntityHungerChest extends TileEntityDaemon {
	
	public TileEntityHungerChest(){
		inv = new ItemStack[27];
	}
	
	@Override
	public String getInvName() {
		return "container.hungerchest";
	}

}
