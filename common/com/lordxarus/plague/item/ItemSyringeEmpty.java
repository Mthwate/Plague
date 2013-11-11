package com.lordxarus.plague.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.lordxarus.plague.DamageSourcePlague;
import com.lordxarus.plague.disease.Disease;
import com.lordxarus.plague.lib.DiseaseHelper;

public class ItemSyringeEmpty extends ItemBaseFood {

	public ItemSyringeEmpty(int id) {
		super(id, 0, 0F, false);
		this.maxStackSize = 1;
		this.setAlwaysEdible();
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	public ItemStack onEaten(ItemStack ItemStack, World world, EntityPlayer player) {
		--ItemStack.stackSize;
		ItemStack itemStack = new ItemStack(ItemPlague.syringeFull);
		itemStack.setTagCompound(new NBTTagCompound());
		itemStack.getTagCompound().setString("owner", player.getEntityName());
		for (Disease disease : DiseaseHelper.getActiveDiseases(player)) {
			itemStack.getTagCompound().setBoolean(disease.getUnlocalizedName(), true);
		}
		if (ItemStack.stackSize <= 0) {
			player.attackEntityFrom(DamageSourcePlague.syringe, 1);
			return itemStack;
		}
		player.inventory.addItemStackToInventory(itemStack);
		player.attackEntityFrom(DamageSourcePlague.syringe, 1);
		return(ItemStack);
	}
	
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return(EnumAction.bow);
	}


}
