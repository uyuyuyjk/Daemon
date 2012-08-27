package tco.daemon.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.src.*;
import net.minecraftforge.client.*;
import tco.daemon.*;

public class ProxyClient extends ProxyCommon {
	@Override
	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/blocks.png");
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/daemonitems.png");
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/feeder.png");
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/matrix.png");
	}
}
