package com.lordxarus.plague.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lordxarus.plague.Plague;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockContainerBase extends BlockContainer {

	Icon frontIcon;
	Icon topBottomIcon;
	Icon sideIcon;

	protected BlockContainerBase(int par1, Material material) {
		super(par1, material);
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		if (side == 0 || side == 1) {
			return topBottomIcon;
		} else if (metadata == 2 && side == 2) {
			return frontIcon;
		} else if (metadata == 3 && side == 5) {
			return frontIcon;
		} else if (metadata == 0 && side == 3) {
			return frontIcon;
		} else if (metadata == 1 && side == 4) {
			return frontIcon;
		} else {
			return sideIcon;
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, Plague.instance, 0, world, x, y, z);
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		int direction = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
		par1World.setBlockMetadataWithNotify(x, y, z, direction, 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		frontIcon = iconRegister.registerIcon(Plague.modid + ":" + getUnlocalizedName().substring(5));
		topBottomIcon = iconRegister.registerIcon(Plague.modid + ":containerTopBottom");
		sideIcon = iconRegister.registerIcon(Plague.modid + ":containerSide");
	}
}
