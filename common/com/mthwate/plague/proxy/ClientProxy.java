package com.mthwate.plague.proxy;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.mthwate.plague.entity.EntityWeaponizedDisease;
import com.mthwate.plague.item.ItemPlague;
import com.mthwate.plague.packet.PacketOutButton;
import com.mthwate.plague.packet.PacketPercentButton;
import com.mthwate.plague.tileentity.TileEntityBaseElectric;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerEntityRenderingHandlers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityWeaponizedDisease.class, new RenderSnowball(ItemPlague.weaponizedDisease));
	}

	@Override
	public void playSound(int dimId, String sound, float posX, float posY, float posZ, float volume, float pitch) {
		World playerWorld = Minecraft.getMinecraft().thePlayer.worldObj;
		if (dimId == playerWorld.provider.dimensionId) {
			Minecraft.getMinecraft().sndManager.playSound(sound, posX, posY, posZ, volume, pitch);
		}
	}

	@Override
	public void spawnParticle(int dimId, String particle, double posX, double posY, double posZ, double velX, double velY, double velZ) {
		World playerWorld = Minecraft.getMinecraft().thePlayer.worldObj;
		if(dimId == playerWorld.provider.dimensionId) {
			playerWorld.spawnParticle(particle, posX, posY, posZ, velX, velY, velZ);
		}
	}

	@Override
	public void updateElectricity(int dimId, int posX, int posY, int posZ, long energy) {
		if (Minecraft.getMinecraft().thePlayer != null) {
			World playerWorld = Minecraft.getMinecraft().thePlayer.worldObj;
			if(dimId == playerWorld.provider.dimensionId) {
				TileEntity tileEntity = playerWorld.getBlockTileEntity(posX, posY, posZ);
				if(tileEntity instanceof TileEntityBaseElectric) {
					TileEntityBaseElectric machine = (TileEntityBaseElectric) tileEntity;
					machine.setEnergy(null, energy);
				}
			}
		}
	}

	@Override
	public void updateOutSlot(int dimId, int posX, int posY, int posZ, int slot, int side) {
		if (Minecraft.getMinecraft().thePlayer != null) {
			World playerWorld = Minecraft.getMinecraft().thePlayer.worldObj;
			if(dimId == playerWorld.provider.dimensionId) {
				TileEntity tileEntity = playerWorld.getBlockTileEntity(posX, posY, posZ);
				if(tileEntity instanceof TileEntityBaseElectric) {
					TileEntityBaseElectric machine = (TileEntityBaseElectric) tileEntity;
					machine.setOutSlot(slot, side);
				}
			}
		}
	}
	
	@Override
	public void clickOutButton(int dimId, int posX, int posY, int posZ, int slot) {
		if(MinecraftServer.getServer() == null) {
			try {
				
				PacketOutButton packetOutButton = new PacketOutButton();
				Packet250CustomPayload packet = packetOutButton.form(dimId, posX, posY, posZ, slot);
			
				PacketDispatcher.sendPacketToServer(packet);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			super.clickOutButton(dimId, posX, posY, posZ, slot);
		}
	}

	@Override
	public void updateOutPercent(int dimId, int posX, int posY, int posZ, int ammount) {
		if (Minecraft.getMinecraft().thePlayer != null) {
			World playerWorld = Minecraft.getMinecraft().thePlayer.worldObj;
			if(dimId == playerWorld.provider.dimensionId) {
				TileEntity tileEntity = playerWorld.getBlockTileEntity(posX, posY, posZ);
				if(tileEntity instanceof TileEntityBaseElectric) {
					TileEntityBaseElectric machine = (TileEntityBaseElectric) tileEntity;
					machine.setOutPercent(ammount);
				}
			}
		}
	}
	
	@Override
	public void clickPercentButton(int dimId, int posX, int posY, int posZ, int ammount) {
		if(MinecraftServer.getServer() == null) {
			try {
				
				PacketPercentButton packetPercentButton = new PacketPercentButton();
				Packet250CustomPayload packet = packetPercentButton.form(dimId, posX, posY, posZ, ammount);
			
				PacketDispatcher.sendPacketToServer(packet);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			super.clickPercentButton(dimId, posX, posY, posZ, ammount);
		}
	}
	
}
