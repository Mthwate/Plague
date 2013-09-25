package com.lordxarus.plague.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.lordxarus.plague.DamageSourcePlague;
import com.lordxarus.plague.DiseaseHelper;
import com.lordxarus.plague.Plague;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSyringeEmpty extends ItemBaseFood {

	public ItemSyringeEmpty(int id) {
		super(id, 0, 0F, false);
		this.maxStackSize = 1;
		this.setAlwaysEdible();
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		--par1ItemStack.stackSize;
		ItemStack itemStack = new ItemStack(Plague.itemSyringeFull);
		itemStack.setTagCompound(new NBTTagCompound());
		itemStack.getTagCompound().setString("owner", par3EntityPlayer.getEntityName());
		for (String disease : DiseaseHelper.getAllDiseases(par3EntityPlayer)) {
			if (disease != ":") {
				itemStack.getTagCompound().setBoolean(disease, true);
			}
		}
		if (par1ItemStack.stackSize <= 0) {
			par3EntityPlayer.attackEntityFrom(DamageSourcePlague.syringe, 1);
			return itemStack;
		}
		par3EntityPlayer.inventory.addItemStackToInventory(itemStack);
		par3EntityPlayer.attackEntityFrom(DamageSourcePlague.syringe, 1);
		return(par1ItemStack);
	}
	
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return(EnumAction.bow);
	}

}
