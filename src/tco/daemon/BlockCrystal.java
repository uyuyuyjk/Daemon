package tco.daemon;

import net.minecraft.src.Material;

public class BlockCrystal extends BlockDaemon {

	public BlockCrystal(int id) {
		super(id, Material.glass);
		setHardness(1.0F);
		setResistance(1.0F);
		setStepSound(soundGlassFootstep);
	}

}
