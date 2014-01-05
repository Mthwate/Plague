package com.mthwate.plague.block;

import com.mthwate.plague.Plague;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockContainerElectricBase extends BlockContainerBase {

	protected BlockContainerElectricBase(int par1, Material material) {
		super(par1, material);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		frontIcon = iconRegister.registerIcon(Plague.modid + ":" + getUnlocalizedName().substring(5));
		topBottomIcon = iconRegister.registerIcon(Plague.modid + ":containerElectricTopBottom");
		sideIcon = iconRegister.registerIcon(Plague.modid + ":containerElectricSide");
	}

}
