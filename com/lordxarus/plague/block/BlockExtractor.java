package com.lordxarus.plague.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.lordxarus.plague.Plague;
import com.lordxarus.plague.tileentity.TileEntityExtractor;

import cpw.mods.fml.common.network.FMLNetworkHandler;

public class BlockExtractor extends BlockContainerBase {
	
	public BlockExtractor(int id) {
		super(id, Material.iron);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(5F);
		this.setResistance(10F);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return(new TileEntityExtractor());
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			FMLNetworkHandler.openGui(player, Plague.instance, 0, world, x, y, z);
		}
		return(true);
	}
}