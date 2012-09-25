package tco.daemon.handlers;

import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler  {

	@Override
	public void onPacketData(NetworkManager manager,
			Packet250CustomPayload packet250, Player player) {
		PacketNBT packet = new PacketNBT();
		packet.readPacket(packet250);
		switch(packet.getId()) {

		}
	}

}
