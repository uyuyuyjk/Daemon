package tco.daemon.client;

import net.minecraftforge.client.MinecraftForgeClient;
import tco.daemon.EntityGateway;
import tco.daemon.ModDaemon;
import tco.daemon.ProxyCommon;
import tco.daemon.render.ModelGateway;
import tco.daemon.render.RenderBrazier;
import tco.daemon.render.RenderGateway;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ProxyClient extends ProxyCommon {
		
	@Override
	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/blocks.png");
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/daemonitems.png");
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/feeder.png");
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/matrix.png");
		
		MinecraftForgeClient.registerItemRenderer(ModDaemon.instance.staff.shiftedIndex, new ItemStaffRenderer());
		
		renderBrazierId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(renderBrazierId, new RenderBrazier());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityGateway.class, new RenderGateway(new ModelGateway()));
	}
	
	@Override
	public boolean isSimulating(){
		return false;
	}
}
