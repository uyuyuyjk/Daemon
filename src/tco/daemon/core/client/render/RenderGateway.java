package tco.daemon.core.client.render;

import net.minecraft.src.RenderLiving;

public class RenderGateway extends RenderLiving {

	public RenderGateway(ModelGateway mainModel){
		super(mainModel, 0);
		//loadTexture(ReferenceConfigs.TEXTURE_GATEWAY);
	}

}
