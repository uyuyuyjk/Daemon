package tco.daemon;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemBlockDaemon extends ItemBlock {

	public ItemBlockDaemon(int id) {
		super(id);
		setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int i) {
	  return i;
	}
		
	@Override
	public String getItemNameIS(ItemStack stack){
		switch(stack.getItemDamage()){
		case 1:
			return "feeder";
		case 2:
			return "hungerChest";
		default:
			return "";
		}
	}

}
