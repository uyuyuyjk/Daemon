package tco.daemon.item;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySmallFireball;
import net.minecraft.src.EntitySnowball;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import tco.daemon.util.ReferenceConfigs;
import tco.daemon.util.energy.DaemonEnergy;

public class ItemAmuletBlaze extends ItemAmuletFire {

	public ItemAmuletBlaze(int id) {
		super(id);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		world.playAuxSFXAtEntity(null, 1009, (int)player.posX, (int)player.posY, (int)player.posZ, 0);
		if (!world.isRemote && DaemonEnergy.drainEnergy(player, ReferenceConfigs.DEATH_ENERGY_BLAZE, 0, 0))
		{
			world.spawnEntityInWorld(new EntitySnowball(world, player));
			EntitySmallFireball fireball =
					new EntitySmallFireball(world, player, 0, 0, 0);
			//start: from entitysnowball
			fireball.setLocationAndAngles(player.posX, player.posY + player.getEyeHeight(), player.posZ, player.rotationYaw, player.rotationPitch);
			fireball.posX -= (MathHelper.cos(fireball.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
			fireball.posY -= 0.10000000149011612D;
			fireball.posZ -= (MathHelper.sin(fireball.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
			fireball.setPosition(fireball.posX, fireball.posY, fireball.posZ);
			fireball.yOffset = 0.0F;
			float var3 = 0.4F;
			fireball.motionX = (-MathHelper.sin(fireball.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(fireball.rotationPitch / 180.0F * (float)Math.PI) * var3);
			fireball.motionZ = (MathHelper.cos(fireball.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(fireball.rotationPitch / 180.0F * (float)Math.PI) * var3);
			fireball.motionY = (-MathHelper.sin((fireball.rotationPitch) / 180.0F * (float)Math.PI) * var3);
			fireball.posY = player.posY + (player.height / 2.0) + 0.5D;
			//end: from entitysnowball
			world.spawnEntityInWorld(fireball);
		}

		return itemStack;
	}


}
