package com.mthwate.plague;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {

		if (packet.channel.equals("PlagueParticle")) {
			spawnParticle(packet);
		} else if (packet.channel.equals("PlagueSound")) {
			playSound(packet);
		}
	}

	private void spawnParticle(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		String particle;
		double posX;
		double posY;
		double posZ;
		double velX;
		double velY;
		double velZ;

		try {
			particle = inputStream.readUTF();
			posX = inputStream.readFloat();
			posY = inputStream.readFloat();
			posZ = inputStream.readFloat();
			velX = inputStream.readFloat();
			velY = inputStream.readFloat();
			velZ = inputStream.readFloat();
			Plague.proxy.spawnParticle(null, particle, posX, posY, posZ, velX, velY, velZ);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void playSound(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		String sound;
		float posX;
		float posY;
		float posZ;
		float volume;
		float pitch;

		try {
			sound = inputStream.readUTF();
			posX = inputStream.readFloat();
			posY = inputStream.readFloat();
			posZ = inputStream.readFloat();
			volume = inputStream.readFloat();
			pitch = inputStream.readFloat();
			Plague.proxy.playSound(null, sound, posX, posY, posZ, volume, pitch);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
