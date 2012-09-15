package tco.daemon;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumRarity;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import tco.daemon.util.ReferenceGui;

public class ItemMatrixContained extends ItemDaemon {

	protected ItemMatrixContained(int id) {
		super(id);
		setMaxStackSize(1);
		setRarity(EnumRarity.rare);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		player.openGui(ModDaemon.instance, ReferenceGui.CONTAINED_MATRIX, world, 0, 0, 0);
		return itemStack;
	}
	
	@Override
	public boolean getShareTag() {
		return true;
	}
}
