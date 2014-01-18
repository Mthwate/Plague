package com.mthwate.plague.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.mthwate.plague.Plague;

public class PacketOutPercent extends Packet {

	float posX;
	float posY;
	float posZ;
	int percent;
	
	@Override
	public String getName() {
		return "outpercent";
	}

	@Override
	void read(DataInputStream inputStream) throws IOException {
		posX = inputStream.readFloat();
		posY = inputStream.readFloat();
		posZ = inputStream.readFloat();
		percent = inputStream.readInt();
	}
	
	public Packet250CustomPayload form(double posX, double posY, double posZ, int percent) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);

		outputStream.writeUTF(this.getName());
		outputStream.writeDouble(posX);
		outputStream.writeDouble(posY);
		outputStream.writeDouble(posZ);
		outputStream.writeInt(percent);
		outputStream.close();
		
		return this.getPayload(bos);
	}
	
	@Override
	void exec() {
		Plague.proxy.updateOutPercent(null, posX, posY, posZ, percent);
	}

}
