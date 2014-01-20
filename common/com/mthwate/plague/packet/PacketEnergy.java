package com.mthwate.plague.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.mthwate.plague.Plague;

public class PacketEnergy extends Packet {

	int dimId;
	int posX;
	int posY;
	int posZ;
	long energy;
	
	@Override
	public String getName() {
		return "energy";
	}

	@Override
	void read(DataInputStream inputStream) throws IOException {
		dimId = inputStream.readInt();
		posX = inputStream.readInt();
		posY = inputStream.readInt();
		posZ = inputStream.readInt();
		energy = inputStream.readLong();
	}
	
	public Packet250CustomPayload form(int dimId, int posX, int posY, int posZ, long energy) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);

		outputStream.writeUTF(this.getName());
		outputStream.writeInt(dimId);
		outputStream.writeInt(posX);
		outputStream.writeInt(posY);
		outputStream.writeInt(posZ);
		outputStream.writeLong(energy);
		outputStream.close();
		
		return this.getPayload(bos);
	}
	
	@Override
	void exec() {
		Plague.proxy.updateElectricity(dimId, posX, posY, posZ, energy);
	}

}
