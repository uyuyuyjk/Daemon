package tco.daemon;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemDaggerRitual extends ItemDagger {

	protected ItemDaggerRitual(int id) {
		super(id);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		player.openGui(ModDaemon.instance, ReferenceGui.PORTABLE_MATRIX, world, 
				(int)player.posX, (int)player.posY, (int)player.posZ);
		return itemStack;
	}
	
	@Override
	public int getDamageVsEntity(Entity entity){
		return 4;
	}
	
}
