package tco.daemon;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ProxyClient extends ProxyCommon {
	@Override
	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/daemonitems.png");
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/blocks.png");
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/feeder.png");
		MinecraftForgeClient.preloadTexture("/tco/daemon/sprites/matrix.png");
	}
}
