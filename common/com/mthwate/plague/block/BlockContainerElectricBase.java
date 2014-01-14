package com.mthwate.plague.block;

import com.mthwate.plague.Plague;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockContainerElectricBase extends BlockContainerBase {
	
	int tier;

	protected BlockContainerElectricBase(int par1, Material material, int tier) {
		super(par1, material);
		this.tier = tier;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		frontIcon = iconRegister.registerIcon(Plague.modid + ":" + getUnlocalizedName().substring(5));
		topBottomIcon = iconRegister.registerIcon(Plague.modid + ":containerElectricTopBottomT" + this.getTier());
		sideIcon = iconRegister.registerIcon(Plague.modid + ":containerElectricSideT" + this.getTier());
	}
	
	public int getTier() {
		return tier;
	}

}
