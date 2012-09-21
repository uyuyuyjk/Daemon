package tco.daemon;

import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;

public class ProxyCommon {

	public int renderBrazierId;

	public void registerRenderInformation() {}

	public void sendToServer(Packet250CustomPayload packet) {}

	public boolean isSimulating() {
		return FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER;
	}
}
