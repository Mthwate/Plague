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

		if (packet.channel.equals("Plague")) {
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			String type;
			try {
				type = inputStream.readUTF();
			
				if(type.equals("sound")) {
					playSound(inputStream);
				} else if (type.equals("particle")) {
					spawnParticle(inputStream);
				} else if(type.equals("energy")) {
					updateElectricity(inputStream);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void spawnParticle(DataInputStream inputStream) {

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

	private void playSound(DataInputStream inputStream) {

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

	private void updateElectricity(DataInputStream inputStream) {

		float posX;
		float posY;
		float posZ;
		long energy;

		try {
			posX = inputStream.readFloat();
			posY = inputStream.readFloat();
			posZ = inputStream.readFloat();
			energy = inputStream.readLong();
			Plague.proxy.updateElectricity(null, posX, posY, posZ, energy);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
