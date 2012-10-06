package tco.daemon.item;

import java.util.List;

import net.minecraft.src.ItemStack;

public class ItemArrowUnstable extends ItemDaemon{

	public ItemArrowUnstable(int id) {
		super(id);
	}

	@Override
	public void addInformation(ItemStack itemStack, List list) {
		list.add("Unstable I");
	}

}
