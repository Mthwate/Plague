package com.mthwate.plague.packet;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.mthwate.plague.Plague;

public abstract class Packet {

	void read(DataInputStream inputStream) throws IOException {}
	
	void exec() {}
	
	Packet250CustomPayload getPayload(ByteArrayOutputStream bos) {
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = Plague.channel;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		return packet;
	}
	
	public String getName() {
		return null;
	}
	
	public void process(DataInputStream inputStream) throws IOException {
		read(inputStream);
		exec();
	}
	
}
