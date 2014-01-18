package com.mthwate.plague.proxy;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.mthwate.plague.GuiHandler;
import com.mthwate.plague.Plague;
import com.mthwate.plague.entity.EntityWeaponizedDisease;
import com.mthwate.plague.item.ItemPlague;
import com.mthwate.plague.packet.PacketOutButton;
import com.mthwate.plague.packet.PacketPercentButton;
import com.mthwate.plague.tileentity.TileEntityBaseElectric;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerEntityRenderingHandlers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityWeaponizedDisease.class, new RenderSnowball(ItemPlague.weaponizedDisease));
	}

	@Override
	public void playSound(World world, String sound, float posX, float posY, float posZ, float volume, float pitch) {
		World playerWorld = Minecraft.getMinecraft().thePlayer.worldObj;
		if (world.provider.dimensionId == playerWorld.provider.dimensionId) {
			Minecraft.getMinecraft().sndManager.playSound(sound, posX, posY, posZ, volume, pitch);
		}
	}

	@Override
	public void spawnParticle(World world, String particle, double posX, double posY, double posZ, double velX, double velY, double velZ) {
		World playerWorld = Minecraft.getMinecraft().thePlayer.worldObj;
		if(world.provider.dimensionId == playerWorld.provider.dimensionId) {
			playerWorld.spawnParticle(particle, posX, posY, posZ, velX, velY, velZ);
		}
	}

	@Override
	public void updateElectricity(World world, double posX, double posY, double posZ, long energy) {
		if (world != null) {
			if (Minecraft.getMinecraft().thePlayer != null) {
				World playerWorld = Minecraft.getMinecraft().thePlayer.worldObj;
				if(world.provider.dimensionId == playerWorld.provider.dimensionId) {
					TileEntity tileEntity = playerWorld.getBlockTileEntity((int) posX, (int) posY, (int) posZ);
					if(tileEntity instanceof TileEntityBaseElectric) {
						TileEntityBaseElectric machine = (TileEntityBaseElectric) tileEntity;
						machine.setEnergy(null, energy);
					}
				}
			}
		}
	}
	
	@Override
	public void init() {
		NetworkRegistry.instance().registerGuiHandler(Plague.instance, new GuiHandler());
	}

	@Override
	public void updateOutSlot(World world, double posX, double posY, double posZ, int slot, int side) {
		if (Minecraft.getMinecraft().thePlayer != null) {
			World playerWorld = Minecraft.getMinecraft().thePlayer.worldObj;
			if(world.provider.dimensionId == playerWorld.provider.dimensionId) {
				TileEntity tileEntity = playerWorld.getBlockTileEntity((int) posX, (int) posY, (int) posZ);
				if(tileEntity instanceof TileEntityBaseElectric) {
					TileEntityBaseElectric machine = (TileEntityBaseElectric) tileEntity;
					machine.setOutSlot(slot, side);
				}
			}
		}
	}
	
	@Override
	public void clickOutButton(World world, int dimId, int posX, int posY, int posZ, int slot) {
		if(world != null && world.isRemote) {
			try {
				
				PacketOutButton packetOutButton = new PacketOutButton();
				Packet250CustomPayload packet = packetOutButton.form(dimId, posX, posY, posZ, slot);
			
				PacketDispatcher.sendPacketToServer(packet);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			TileEntity tileEntity = MinecraftServer.getServer().worldServerForDimension(dimId).getBlockTileEntity(posX, posY, posZ);
			if (tileEntity instanceof TileEntityBaseElectric) {
				TileEntityBaseElectric tileEntityElectric = (TileEntityBaseElectric) tileEntity;
				tileEntityElectric.changeOutDirection(slot);
				Plague.proxy.updateOutSlot(MinecraftServer.getServer().worldServerForDimension(dimId), posX, posY, posZ, slot, tileEntityElectric.getOutDirection(slot));
			}
		}
	}

	@Override
	public void updateOutPercent(World world, double posX, double posY, double posZ, int ammount) {
		if (Minecraft.getMinecraft().thePlayer != null) {
			World playerWorld = Minecraft.getMinecraft().thePlayer.worldObj;
			if(world.provider.dimensionId == playerWorld.provider.dimensionId) {
				TileEntity tileEntity = playerWorld.getBlockTileEntity((int) posX, (int) posY, (int) posZ);
				if(tileEntity instanceof TileEntityBaseElectric) {
					TileEntityBaseElectric machine = (TileEntityBaseElectric) tileEntity;
					machine.setOutPercent(ammount);
				}
			}
		}
	}
	
	@Override
	public void clickPercentButton(World world, int dimId, int posX, int posY, int posZ, int ammount) {
		if(world != null && world.isRemote) {
			try {
				
				PacketPercentButton packetPercentButton = new PacketPercentButton();
				Packet250CustomPayload packet = packetPercentButton.form(dimId, posX, posY, posZ, ammount);
			
				PacketDispatcher.sendPacketToServer(packet);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			TileEntity tileEntity = MinecraftServer.getServer().worldServerForDimension(dimId).getBlockTileEntity(posX, posY, posZ);
			if (tileEntity instanceof TileEntityBaseElectric) {
				TileEntityBaseElectric tileEntityElectric = (TileEntityBaseElectric) tileEntity;
				tileEntityElectric.increaseOutPercent(ammount);
				Plague.proxy.updateOutPercent(MinecraftServer.getServer().worldServerForDimension(dimId), posX, posY, posZ, tileEntityElectric.getOutPercent());
			}
		}
	}
	
}
