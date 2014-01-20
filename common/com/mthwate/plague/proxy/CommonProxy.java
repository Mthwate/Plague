package com.mthwate.plague.proxy;

import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

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

	public void playSound(int dimId, String sound, float posX, float posY, float posZ, float volume, float pitch) {
		try {
			
			PacketSound packetSound = new PacketSound();
			Packet250CustomPayload packet = packetSound.form(dimId, sound, posX, posY, posZ, volume, pitch);
			
			PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 64, dimId, packet);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void spawnParticle(int dimId, String particle, double posX, double posY, double posZ, double velX, double velY, double velZ) {
		try {
			
			PacketParticle packetParticle = new PacketParticle();
			Packet250CustomPayload packet = packetParticle.form(dimId, particle, posX, posY, posZ, velX, velY, velZ);
		
			PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 64, dimId, packet);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateElectricity(int dimId, int posX, int posY, int posZ, long energy) {
		try {
			
			PacketEnergy packetEnergy = new PacketEnergy();
			Packet250CustomPayload packet = packetEnergy.form(dimId, posX, posY, posZ, energy);
		
			PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 64, dimId, packet);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateOutSlot(int dimId, int posX, int posY, int posZ, int slot, int side) {
		try {
			
			PacketOutSlot packetOutSlot = new PacketOutSlot();
			Packet250CustomPayload packet = packetOutSlot.form(dimId, posX, posY, posZ, slot, side);
		
			PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 64, dimId, packet);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clickOutButton(int dimId, int posX, int posY, int posZ, int slot) {
		TileEntity tileEntity = MinecraftServer.getServer().worldServerForDimension(dimId).getBlockTileEntity(posX, posY, posZ);
		if (tileEntity instanceof TileEntityBaseElectric) {
			TileEntityBaseElectric tileEntityElectric = (TileEntityBaseElectric) tileEntity;
			tileEntityElectric.changeOutDirection(slot);
			Plague.proxy.updateOutSlot(dimId, posX, posY, posZ, slot, tileEntityElectric.getOutDirection(slot));
		}
	}

	public void updateOutPercent(int dimId, int posX, int posY, int posZ, int ammount) {
		try {
			
			PacketOutPercent packetOutPercent = new PacketOutPercent();
			Packet250CustomPayload packet = packetOutPercent.form(dimId, posX, posY, posZ, ammount);
		
			PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 64, dimId, packet);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clickPercentButton(int dimId, int posX, int posY, int posZ, int ammount) {
		TileEntity tileEntity = MinecraftServer.getServer().worldServerForDimension(dimId).getBlockTileEntity(posX, posY, posZ);
		if (tileEntity instanceof TileEntityBaseElectric) {
			TileEntityBaseElectric tileEntityElectric = (TileEntityBaseElectric) tileEntity;
			tileEntityElectric.increaseOutPercent(ammount);
			Plague.proxy.updateOutPercent(dimId, posX, posY, posZ, tileEntityElectric.getOutPercent());
		}
	}

}
