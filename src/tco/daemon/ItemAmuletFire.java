package tco.daemon;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import tco.daemon.energy.DaemonEnergy;
import tco.daemon.util.ReferenceConfigs;

public class ItemAmuletFire extends ItemDaemon {

	public ItemAmuletFire(int id) {
		super(id);
		setMaxStackSize(1);
	}

	@Override
	public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer player, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		//start: ItemFlintAndSteel code (slightly modified)
		if (par7 == 0)
		{
			--par5;
		}
		if (par7 == 1)
		{
			++par5;
		}
		if (par7 == 2)
		{
			--par6;
		}
		if (par7 == 3)
		{
			++par6;
		}
		if (par7 == 4)
		{
			--par4;
		}
		if (par7 == 5)
		{
			++par4;
		}
		if (player.canPlayerEdit(par4, par5, par6) &&
				DaemonEnergy.drainEnergy(player, ReferenceConfigs.DEATH_ENERGY_FIRE, 0, 0))
		{
			int var11 = par3World.getBlockId(par4, par5, par6);

			if (var11 == 0)
			{
				par3World.playSoundEffect(par4 + 0.5D, par5 + 0.5D, par6 + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				par3World.setBlockWithNotify(par4, par5, par6, Block.fire.blockID);
			}
			return true;
		}
		//end: ItemFlintAndSteel code
		return false;
	}

}
