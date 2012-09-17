package tco.daemon;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class ProxyCommon {

	public int renderBrazierId;

	public void registerRenderInformation() {}

	public void sendToServer(Packet250CustomPayload packet) {}

	public boolean isSimulating() {
		return true;
	}
}
