package com.lordxarus.plague.proxy;

import net.minecraft.client.renderer.entity.RenderSnowball;

import com.lordxarus.plague.entity.EntityWeaponizedDisease;
import com.lordxarus.plague.item.ItemPlague;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerEntityRenderingHandlers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityWeaponizedDisease.class, new RenderSnowball(ItemPlague.weaponizedDisease));
	}

}
