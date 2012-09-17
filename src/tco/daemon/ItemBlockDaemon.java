package tco.daemon;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import tco.daemon.util.ReferenceGui;

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
		return ReferenceGui.values()[stack.getItemDamage()].getName();
	}

}
