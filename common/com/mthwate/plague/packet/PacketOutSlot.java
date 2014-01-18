package com.mthwate.plague.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.mthwate.plague.Plague;

public class PacketOutSlot extends Packet {

	float posX;
	float posY;
	float posZ;
	int slot;
	int side;
	
	@Override
	public String getName() {
		return "outSlot";
	}

	@Override
	void read(DataInputStream inputStream) throws IOException {
		posX = inputStream.readFloat();
		posY = inputStream.readFloat();
		posZ = inputStream.readFloat();
		slot = inputStream.readInt();
		side = inputStream.readInt();
	}
	
	public Packet250CustomPayload form(double posX, double posY, double posZ, int slot, int side) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);

		outputStream.writeUTF(this.getName());
		outputStream.writeDouble(posX);
		outputStream.writeDouble(posY);
		outputStream.writeDouble(posZ);
		outputStream.writeInt(slot);
		outputStream.writeInt(side);
		outputStream.close();
		
		return this.getPayload(bos);
	}
	
	@Override
	void exec() {
		Plague.proxy.updateOutSlot(null, posX, posY, posZ, slot, side);
	}
}
