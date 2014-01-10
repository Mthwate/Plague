package com.mthwate.plague.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.mthwate.plague.tileentity.TileEntityExtractorElectric;

public class BlockExtractorElectric extends BlockContainerElectricBase {

	public BlockExtractorElectric(int id, int tier) {
		super(id, Material.iron, tier);
		setCreativeTab(CreativeTabs.tabBlock);
		setHardness(5F);
		setResistance(10F);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityExtractorElectric();
	}

}
