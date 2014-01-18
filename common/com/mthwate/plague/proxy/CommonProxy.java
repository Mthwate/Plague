package com.mthwate.plague.proxy;

import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.mthwate.plague.Plague;
import com.mthwate.plague.packet.PacketEnergy;
import com.mthwate.plague.packet.PacketOutPercent;
import com.mthwate.plague.packet.PacketOutSlot;
import com.mthwate.plague.packet.PacketParticle;
import com.mthwate.plague.packet.PacketSound;
import com.mthwate.plague.tileentity.TileEntityBaseElectric;

import cpw.mods.fml.common.network.PacketDispatcher;

public class CommonProxy {

	public void registerEntityRenderingHandlers() {}

	public void playSound(World world, String sound, float posX, float posY, float posZ, float volume, float pitch) {
		try {
			
			PacketSound packetSound = new PacketSound();
			Packet250CustomPayload packet = packetSound.form(sound, posX, posY, posZ, volume, pitch);
			
			PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 64, world.provider.dimensionId, packet);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void spawnParticle(World world, String particle, double posX, double posY, double posZ, double velX, double velY, double velZ) {
		try {
			
			PacketParticle packetParticle = new PacketParticle();
			Packet250CustomPayload packet = packetParticle.form(particle, posX, posY, posZ, velX, velY, velZ);
		
			PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 64, world.provider.dimensionId, packet);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateElectricity(World world, double posX, double posY, double posZ, long energy) {
		try {
			
			PacketEnergy packetEnergy = new PacketEnergy();
			Packet250CustomPayload packet = packetEnergy.form(posX, posY, posZ, energy);
		
			PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 64, world.provider.dimensionId, packet);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateOutSlot(World world, double posX, double posY, double posZ, int slot, int side) {
		try {
			
			PacketOutSlot packetOutSlot = new PacketOutSlot();
			Packet250CustomPayload packet = packetOutSlot.form(posX, posY, posZ, slot, side);
		
			PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 64, world.provider.dimensionId, packet);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clickOutButton(World world, int dimId, int posX, int posY, int posZ, int slot) {
		TileEntity tileEntity = MinecraftServer.getServer().worldServerForDimension(dimId).getBlockTileEntity(posX, posY, posZ);
		if (tileEntity instanceof TileEntityBaseElectric) {
			TileEntityBaseElectric tileEntityElectric = (TileEntityBaseElectric) tileEntity;
			tileEntityElectric.changeOutDirection(slot);
			Plague.proxy.updateOutSlot(MinecraftServer.getServer().worldServerForDimension(dimId), posX, posY, posZ, slot, tileEntityElectric.getOutDirection(slot));
		}
	}

	public void updateOutPercent(World world, double posX, double posY, double posZ, int ammount) {
		try {
			
			PacketOutPercent packetOutPercent = new PacketOutPercent();
			Packet250CustomPayload packet = packetOutPercent.form(posX, posY, posZ, ammount);
		
			PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 64, world.provider.dimensionId, packet);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clickPercentButton(World world, int dimId, int posX, int posY, int posZ, int ammount) {
		TileEntity tileEntity = MinecraftServer.getServer().worldServerForDimension(dimId).getBlockTileEntity(posX, posY, posZ);
		if (tileEntity instanceof TileEntityBaseElectric) {
			TileEntityBaseElectric tileEntityElectric = (TileEntityBaseElectric) tileEntity;
			tileEntityElectric.increaseOutPercent(ammount);
			Plague.proxy.updateOutPercent(MinecraftServer.getServer().worldServerForDimension(dimId), posX, posY, posZ, ammount);
		}
	}

}
