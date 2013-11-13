package com.lordxarus.plague.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.lordxarus.plague.Plague;
import com.lordxarus.plague.tileentity.TileEntityAnalyzer;

import cpw.mods.fml.common.network.FMLNetworkHandler;

public class BlockAnalyzer extends BlockContainerBase {
	
	public BlockAnalyzer(int id) {
		super(id, Material.iron);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(5F);
		this.setResistance(10F);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return(new TileEntityAnalyzer());
	}
}