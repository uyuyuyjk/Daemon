package tco.daemon;

import net.minecraft.src.*;

public class TileEntityHungerChest extends TileEntityDaemon {
	
	public TileEntityHungerChest(){
		inv = new ItemStack[36];
	}
	
	@Override
	public String getInvName() {
		return "container.hungerchest";
	}

}
