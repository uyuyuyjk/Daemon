package tco.daemon;

import net.minecraft.src.Entity;

public class ItemDagger extends ItemDaemon {

	protected ItemDagger(int id) {
		super(id);
		setMaxStackSize(1);
		setFull3D();
	}
				
	@Override
	public int getDamageVsEntity(Entity entity){
		return 2;
	}
	
}
