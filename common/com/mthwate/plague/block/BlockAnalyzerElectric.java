package com.mthwate.plague.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.mthwate.plague.tileentity.TileEntityAnalyzerElectric;

public class BlockAnalyzerElectric extends BlockContainerBase {

	public BlockAnalyzerElectric(int id) {
		super(id, Material.iron);
		setCreativeTab(CreativeTabs.tabBlock);
		setHardness(5F);
		setResistance(10F);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityAnalyzerElectric();
	}

}
