package com.mthwate.plague.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.mthwate.plague.Plague;

public class PacketSound extends Packet {

	int dimId;
	String sound;
	float posX;
	float posY;
	float posZ;
	float volume;
	float pitch;
	
	@Override
	public String getName() {
		return "sound";
	}

	@Override
	void read(DataInputStream inputStream) throws IOException {
		dimId = inputStream.readInt();
		sound = inputStream.readUTF();
		posX = inputStream.readFloat();
		posY = inputStream.readFloat();
		posZ = inputStream.readFloat();
		volume = inputStream.readFloat();
		pitch = inputStream.readFloat();
	}
	
	public Packet250CustomPayload form(int dimId, String sound, float x, float y, float z, float volume, float pitch) throws IOException {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);

		outputStream.writeUTF(this.getName());
		outputStream.writeInt(dimId);
		outputStream.writeUTF(sound);
		outputStream.writeFloat(x);
		outputStream.writeFloat(y);
		outputStream.writeFloat(z);
		outputStream.writeFloat(volume);
		outputStream.writeFloat(pitch);
		outputStream.close();
		
		return this.getPayload(bos);
	}

	@Override
	void exec() {
		Plague.proxy.playSound(dimId, sound, posX, posY, posZ, volume, pitch);
	}
	
}
