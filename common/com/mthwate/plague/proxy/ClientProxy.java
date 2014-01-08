package com.mthwate.plague.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.mthwate.plague.GuiHandler;
import com.mthwate.plague.Plague;
import com.mthwate.plague.entity.EntityWeaponizedDisease;
import com.mthwate.plague.item.ItemPlague;
import com.mthwate.plague.tileentity.TileEntityBaseElectric;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerEntityRenderingHandlers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityWeaponizedDisease.class, new RenderSnowball(ItemPlague.weaponizedDisease));
	}

	@Override
	public void playSound(World world, String sound, float x, float y, float z, float volume, float pitch) {
		World playerWorld = Minecraft.getMinecraft().thePlayer.worldObj;
		if (world.provider.dimensionId == playerWorld.provider.dimensionId) {
			Minecraft.getMinecraft().sndManager.playSound(sound, x, y, z, volume, pitch);
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
	
	@Override
	public void init() {
		NetworkRegistry.instance().registerGuiHandler(Plague.instance, new GuiHandler());
	}
}
