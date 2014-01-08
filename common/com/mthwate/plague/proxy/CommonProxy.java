package com.mthwate.plague.proxy;

import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import com.mthwate.plague.packet.PacketEnergy;
import com.mthwate.plague.packet.PacketParticle;
import com.mthwate.plague.packet.PacketSound;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class CommonProxy {

	public void registerEntityRenderingHandlers() {}

	public void playSound(World world, String sound, float x, float y, float z, float volume, float pitch) {
		try {
			
			PacketSound packetSound = new PacketSound();
			Packet250CustomPayload packet = packetSound.form(sound, x, y, z, volume, pitch);
			
			for (Object object : world.playerEntities) {
				if(object instanceof Player) {
					Player player = (Player) object;
					PacketDispatcher.sendPacketToPlayer(packet, player);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void spawnParticle(World world, String particle, double posX, double posY, double posZ, double velX, double velY, double velZ) {
		try {
			
			PacketParticle packetParticle = new PacketParticle();
			Packet250CustomPayload packet = packetParticle.form(particle, posX, posY, posZ, velX, velY, velZ);
		
			for (Object object : world.playerEntities) {
				if(object instanceof Player) {
					Player player = (Player) object;
					PacketDispatcher.sendPacketToPlayer(packet, player);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateElectricity(World world, double posX, double posY, double posZ, long energy) {
		try {
			
			PacketEnergy packetEnergy = new PacketEnergy();
			Packet250CustomPayload packet = packetEnergy.form(posX, posY, posZ, energy);
		
			for (Object object : world.playerEntities) {
				if(object instanceof Player) {
					Player player = (Player) object;
					PacketDispatcher.sendPacketToPlayer(packet, player);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void init() {}

}
