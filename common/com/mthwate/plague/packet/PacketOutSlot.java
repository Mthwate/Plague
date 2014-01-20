package com.mthwate.plague.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.mthwate.plague.Plague;

public class PacketOutSlot extends Packet {

	int dimId;
	int posX;
	int posY;
	int posZ;
	int slot;
	int side;
	
	@Override
	public String getName() {
		return "outSlot";
	}

	@Override
	void read(DataInputStream inputStream) throws IOException {
		dimId = inputStream.readInt();
		posX = inputStream.readInt();
		posY = inputStream.readInt();
		posZ = inputStream.readInt();
		slot = inputStream.readInt();
		side = inputStream.readInt();
	}
	
	public Packet250CustomPayload form(int dimId, int posX, int posY, int posZ, int slot, int side) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);

		outputStream.writeUTF(this.getName());
		outputStream.writeInt(dimId);
		outputStream.writeInt(posX);
		outputStream.writeInt(posY);
		outputStream.writeInt(posZ);
		outputStream.writeInt(slot);
		outputStream.writeInt(side);
		outputStream.close();
		
		return this.getPayload(bos);
	}
	
	@Override
	void exec() {
		Plague.proxy.updateOutSlot(dimId, posX, posY, posZ, slot, side);
	}
}
