package com.mthwate.plague.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.world.World;

import com.mthwate.plague.entity.EntityWeaponizedDisease;
import com.mthwate.plague.item.ItemPlague;

import cpw.mods.fml.client.registry.RenderingRegistry;

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
	
}
