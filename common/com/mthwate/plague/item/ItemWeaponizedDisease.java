package com.mthwate.plague.item;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.mthwate.plague.entity.EntityWeaponizedDisease;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWeaponizedDisease extends ItemBase {

	public ItemWeaponizedDisease(int id) {
		super(id);
		maxStackSize = 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack itemStack, EntityPlayer player, List dataList, boolean bool) {
		if (itemStack.getTagCompound() != null) {
			double effectiveness = itemStack.getTagCompound().getDouble("effectiveness");

			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.FLOOR);

			String roundedEffectiveness = df.format(effectiveness);

			dataList.add(roundedEffectiveness + "% Effective");
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (!par3EntityPlayer.capabilities.isCreativeMode) {
			--par1ItemStack.stackSize;
		}

		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!par2World.isRemote) {
			EntityWeaponizedDisease entity = new EntityWeaponizedDisease(par2World, par3EntityPlayer);
			if (par1ItemStack.getTagCompound() != null) {
				entity.getEntityData().setDouble("effectiveness", par1ItemStack.getTagCompound().getDouble("effectiveness"));
				entity.getEntityData().setString("disease", par1ItemStack.getTagCompound().getString("disease"));
			}
			par2World.spawnEntityInWorld(entity);
		}

		return par1ItemStack;
	}

}