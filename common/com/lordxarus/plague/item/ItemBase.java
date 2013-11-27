package com.lordxarus.plague.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

import com.lordxarus.plague.Plague;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBase extends Item {

	public ItemBase(int id) {
		super(id);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon(Plague.modid + ":" + this.getUnlocalizedName().substring(5));
	}

}
