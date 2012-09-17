package tco.daemon;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;

public class BlockCrystal extends Block {

	public BlockCrystal(int id) {
		super(id, Material.glass);
		setHardness(1.0F);
		setResistance(1.0F);
		setStepSound(soundGlassFootstep);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

}
