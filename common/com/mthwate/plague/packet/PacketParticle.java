package com.mthwate.plague.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.mthwate.plague.Plague;

public class PacketParticle extends Packet {

	int dimId;
	String particle;
	double posX;
	double posY;
	double posZ;
	double velX;
	double velY;
	double velZ;
	
	@Override
	public String getName() {
		return "particle";
	}
	
	public Packet250CustomPayload form(int dimId, String particle, double posX, double posY, double posZ, double velX, double velY, double velZ) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);

		outputStream.writeUTF(this.getName());
		outputStream.writeInt(dimId);
		outputStream.writeUTF(particle);
		outputStream.writeDouble(posX);
		outputStream.writeDouble(posY);
		outputStream.writeDouble(posZ);
		outputStream.writeDouble(velX);
		outputStream.writeDouble(velY);
		outputStream.writeDouble(velZ);
		outputStream.close();
		
		return this.getPayload(bos);
	}

	@Override
	void read(DataInputStream inputStream) throws IOException {
		dimId = inputStream.readInt();
		particle = inputStream.readUTF();
		posX = inputStream.readDouble();
		posY = inputStream.readDouble();
		posZ = inputStream.readDouble();
		velX = inputStream.readDouble();
		velY = inputStream.readDouble();
		velZ = inputStream.readDouble();
	}
	
	@Override
	void exec() {
		Plague.proxy.spawnParticle(dimId, particle, posX, posY, posZ, velX, velY, velZ);
	}

}
