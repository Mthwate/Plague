package com.mthwate.plague.proxy;

import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.mthwate.plague.Plague;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class CommonProxy {

	public void registerEntityRenderingHandlers() {}

	public void playSound(World world, String sound, float x, float y, float z, float volume, float pitch) {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeUTF("sound");
			outputStream.writeUTF(sound);
			outputStream.writeFloat(x);
			outputStream.writeFloat(y);
			outputStream.writeFloat(z);
			outputStream.writeFloat(volume);
			outputStream.writeFloat(pitch);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = Plague.channel;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		for (Object object : world.playerEntities) {
			if(object instanceof Player) {
				Player player = (Player) object;
				PacketDispatcher.sendPacketToPlayer(packet, player);
			}
		}
	}

	public void spawnParticle(World world, String particle, double posX, double posY, double posZ, double velX, double velY, double velZ) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeUTF("particle");
			outputStream.writeUTF(particle);
			outputStream.writeDouble(posX);
			outputStream.writeDouble(posY);
			outputStream.writeDouble(posZ);
			outputStream.writeDouble(velX);
			outputStream.writeDouble(velY);
			outputStream.writeDouble(velZ);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = Plague.channel;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		for (Object object : world.playerEntities) {
			if(object instanceof Player) {
				Player player = (Player) object;
				PacketDispatcher.sendPacketToPlayer(packet, player);
			}
		}
	}

	public void updateElectricity(World world, double posX, double posY, double posZ, long energy) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeUTF("energy");
			outputStream.writeDouble(posX);
			outputStream.writeDouble(posY);
			outputStream.writeDouble(posZ);
			outputStream.writeLong(energy);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = Plague.channel;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		for (Object object : world.playerEntities) {
			if(object instanceof Player) {
				Player player = (Player) object;
				PacketDispatcher.sendPacketToPlayer(packet, player);
			}
		}
	}
	
	public void init() {}

}
