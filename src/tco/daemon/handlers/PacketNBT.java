package tco.daemon.handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet250CustomPayload;
import tco.daemon.util.ReferenceConfigs;
import cpw.mods.fml.common.FMLLog;

public class PacketNBT {

	private byte id;
	private NBTTagCompound tagCompound;

	public PacketNBT() {}

	public PacketNBT(byte packetId, NBTTagCompound tag) {
		id = packetId;
		tagCompound = tag;
	}

	public PacketNBT(byte packetId) {
		this(packetId, new NBTTagCompound());
	}

	public byte getId(){
		return id;
	}

	public NBTTagCompound getTagCompound(){
		return tagCompound;
	}

	public void writeData(DataOutputStream outputStream) {}

	public Packet250CustomPayload writePacket(){
		Packet250CustomPayload packet = new Packet250CustomPayload();

		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteArray);
		try {
			byte[] tagData = CompressedStreamTools.compress(tagCompound);
			out.write(id); //1. Write id
			out.writeShort((short) tagData.length); //2. Write length
			out.write(tagData); //3. Write tag
			packet.channel = ReferenceConfigs.CHANNEL;
			packet.data = byteArray.toByteArray();
			packet.length = out.size();
		} catch(Exception e) {
			FMLLog.severe("Failed writing packet data", e);
		}
		return packet;
	}

	public void readPacket(Packet250CustomPayload packet250){
		ByteArrayInputStream byteArray = new ByteArrayInputStream(packet250.data);
		DataInputStream in = new DataInputStream(byteArray);
		try {
			id = in.readByte();
			short size = in.readShort();
			byte[] tagData = new byte[size];
			in.readFully(tagData);
			tagCompound = CompressedStreamTools.decompress(tagData);
		} catch (IOException e) {
			FMLLog.severe("Failed reading packet data", e);
		}
	}

}
