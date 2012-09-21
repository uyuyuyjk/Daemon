package tco.daemon;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import tco.daemon.util.ReferenceTiles;

public class ItemDaggerRitual extends ItemDagger {

	protected ItemDaggerRitual(int id,EnumToolMaterial mat) {
		super(id, mat);
		setMaxDamage(0);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		player.openGui(ModDaemon.instance, ReferenceTiles.PORTABLE_MATRIX, world,
				(int)player.posX, (int)player.posY, (int)player.posZ);
		return itemStack;
	}

}
