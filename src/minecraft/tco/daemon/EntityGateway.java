package tco.daemon;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;

public class EntityGateway extends EntityLiving {

	public EntityGateway(World world) {
		super(world);
        texture = ReferenceConfigs.TEXTURE_GATEWAY;
        setSize(2.0F, 2.5F);
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
	}
	
}
