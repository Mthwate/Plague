package com.lordxarus.plague.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;

import com.lordxarus.plague.Plague;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBaseFood extends ItemFood {

	public ItemBaseFood(int id, int heal, float saturation, boolean wolfFood) {
		super(id, heal, saturation, wolfFood);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon(Plague.modid + ":" + this.getUnlocalizedName().substring(5));
	}

}
