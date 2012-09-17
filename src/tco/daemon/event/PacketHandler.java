package tco.daemon.event;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

//Sending: new PacketDaemon(id).writePacket()
//Recieving: PacketDaemon packet = PacketHandler.readPacket(packet250)
//switch(packet.id){ case ID: packet.readData() }
public class PacketHandler implements IPacketHandler  {

	public final Map<Byte, PacketDaemon> packetMapping = new HashMap<Byte, PacketDaemon>();

	@Override
	public void onPacketData(NetworkManager manager,
			Packet250CustomPayload packet250, Player player) {
		PacketDaemon packet = readPacket(packet250);
	}

	private PacketDaemon readPacket(Packet250CustomPayload packet250){
		PacketDaemon packet = new PacketDaemon();
		packet.readPacket(packet250);
		return packet;
	}

}
