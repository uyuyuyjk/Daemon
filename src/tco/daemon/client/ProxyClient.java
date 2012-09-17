package tco.daemon.client;

import net.minecraftforge.client.MinecraftForgeClient;
import tco.daemon.EntityGateway;
import tco.daemon.ModDaemon;
import tco.daemon.ProxyCommon;
import tco.daemon.client.render.ItemStaffRenderer;
import tco.daemon.client.render.ModelGateway;
import tco.daemon.client.render.RenderBrazier;
import tco.daemon.client.render.RenderGateway;
import tco.daemon.event.PacketDaemon;
import tco.daemon.util.ReferenceConfigs;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;

public class ProxyClient extends ProxyCommon {

	@Override
	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture(ReferenceConfigs.TEXTURE_BLOCKS);
		MinecraftForgeClient.preloadTexture(ReferenceConfigs.TEXTURE_ITEMS);
		MinecraftForgeClient.preloadTexture(ReferenceConfigs.TEXTURE_GATEWAY);
		MinecraftForgeClient.preloadTexture(ReferenceConfigs.GUI_MATRIX);
		MinecraftForgeClient.preloadTexture(ReferenceConfigs.GUI_FEEDER);
		MinecraftForgeClient.preloadTexture(ReferenceConfigs.GUI_HUNGER_CHEST);
		MinecraftForgeClient.preloadTexture(ReferenceConfigs.GUI_DECOMPOSER);

		MinecraftForgeClient.registerItemRenderer(ModDaemon.instance.staff.shiftedIndex, new ItemStaffRenderer());

		renderBrazierId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(renderBrazierId, new RenderBrazier());

		RenderingRegistry.registerEntityRenderingHandler(EntityGateway.class, new RenderGateway(new ModelGateway()));
	}

	public void sendToServer(PacketDaemon packet) {
		PacketDispatcher.sendPacketToServer(packet.writePacket());
	}

	@Override
	public boolean isSimulating(){
		return false;
	}
}
