package tco.daemon;

import net.minecraft.src.EntityEnderPearl;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemOrbUnstable extends ItemDaemon {

	protected ItemOrbUnstable(int id) {
		super(id);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,
			EntityPlayer player) {
		if (player.ridingEntity == null) {
			world.playSoundAtEntity(player, "random.bow", 0.5F,
					0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!world.isRemote) {
				world.spawnEntityInWorld(new EntityEnderPearl(world, player));
			}

			return itemStack;
		}
		return itemStack;
	}

}
