package com.mthwate.plague.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.mthwate.plague.Plague;

public class PacketEnergy extends Packet {

	float posX;
	float posY;
	float posZ;
	long energy;
	
	@Override
	public String getName() {
		return "energy";
	}

	@Override
	void read(DataInputStream inputStream) throws IOException {
		posX = inputStream.readFloat();
		posY = inputStream.readFloat();
		posZ = inputStream.readFloat();
		energy = inputStream.readLong();
	}
	
	public Packet250CustomPayload form(double posX, double posY, double posZ, long energy) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);

		outputStream.writeUTF(this.getName());
		outputStream.writeDouble(posX);
		outputStream.writeDouble(posY);
		outputStream.writeDouble(posZ);
		outputStream.writeLong(energy);
		outputStream.close();
		
		return this.getPayload(bos);
	}
	
	@Override
	void exec() {
		Plague.proxy.updateElectricity(null, posX, posY, posZ, energy);
	}

}
