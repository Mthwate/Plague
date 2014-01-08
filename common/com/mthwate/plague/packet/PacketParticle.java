package com.mthwate.plague.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.mthwate.plague.Plague;

public class PacketParticle extends Packet {

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
	
	public Packet250CustomPayload form(String particle, double posX, double posY, double posZ, double velX, double velY, double velZ) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);

		outputStream.writeUTF(this.getName());
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
		particle = inputStream.readUTF();
		posX = inputStream.readFloat();
		posY = inputStream.readFloat();
		posZ = inputStream.readFloat();
		velX = inputStream.readFloat();
		velY = inputStream.readFloat();
		velZ = inputStream.readFloat();
	}
	
	@Override
	void exec() {
		Plague.proxy.spawnParticle(null, particle, posX, posY, posZ, velX, velY, velZ);
	}

}
