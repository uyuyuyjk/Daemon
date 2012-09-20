package tco.daemon.handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.Packet250CustomPayload;
import tco.daemon.util.ReferenceConfigs;

public class PacketDaemon {

	public byte id;
	DataInputStream input;

	public PacketDaemon() {}

	public PacketDaemon(byte packetId) {
		id = packetId;
	}

	public void writeData(DataOutputStream outputStream) {}

	public Packet250CustomPayload writePacket(){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		try {
			out.writeByte(id);
			writeData(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = ReferenceConfigs.CHANNEL;
		packet.data = baos.toByteArray();
		packet.length = baos.size();
		return packet;
	}

	public void readPacket(Packet250CustomPayload packet250){
		ByteArrayInputStream bais = new ByteArrayInputStream(packet250.data);
		DataInputStream in = new DataInputStream(bais);
		PacketDaemon packet = null;
		try {
			id = in.readByte();
		} catch (IOException e) {
			e.printStackTrace();
		}
		input = in;
	}

}
