package tco.daemon.client;

import net.minecraftforge.client.MinecraftForgeClient;
import tco.daemon.ProxyCommon;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ProxyClient extends ProxyCommon {
		
	@Override
	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/blocks.png");
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/daemonitems.png");
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/feeder.png");
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/matrix.png");
		
		renderBrazierId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(renderBrazierId, new RenderBrazier());
	}
}
