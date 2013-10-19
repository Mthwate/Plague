package com.lordxarus.plague.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.lordxarus.plague.DamageSourcePlague;
import com.lordxarus.plague.Plague;
import com.lordxarus.plague.disease.Disease;
import com.lordxarus.plague.lib.DiseaseHelper;
import com.mthwate.bookcase.Rand;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCure extends ItemBaseFood {

	public ItemCure(int id) {
		super(id, 0, 0F, false);
		this.maxStackSize = 1;
		this.setAlwaysEdible();
	}

	public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player) {
		--itemStack.stackSize;
		
		cure(itemStack, player);
		
		ItemStack itemStackSyringeEmpty = new ItemStack(Plague.itemSyringeEmpty);
		if (itemStack.stackSize <= 0) {
			player.attackEntityFrom(DamageSourcePlague.syringe, 1);
			return itemStackSyringeEmpty;
		}
		player.inventory.addItemStackToInventory(itemStackSyringeEmpty);
		player.attackEntityFrom(DamageSourcePlague.syringe, 1);
		return(itemStack);
	}
	
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return(EnumAction.bow);
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List dataList, boolean bool) {
		if (itemStack.getTagCompound() != null) {
			String diseaseName = itemStack.getTagCompound().getString("displayDisease");
			
			for (Disease disease : Plague.diseases) {
				if (disease.getUnlocalizedName().equals(diseaseName)) {
					diseaseName = disease.getName();
				}
			}
			
			dataList.add(itemStack.getDisplayName() + " for " + diseaseName);
		}
	}
	
	void cure(ItemStack itemStack, EntityPlayer player) {
		String displayDisease = itemStack.getTagCompound().getString("displayDisease");
		String cureDisease = itemStack.getTagCompound().getString("cureDisease");
		
		if (displayDisease.equals(cureDisease)) {
			for (Disease disease : Plague.diseases) {
				if (disease.getUnlocalizedName().equals(cureDisease)) {
					DiseaseHelper.setDiseaseDuration(player, disease, -1);
				}
			}
		} else {
			int index = Rand.nextInt(Plague.diseases.size());
			DiseaseHelper.addDisease(player, Plague.diseases.get(index));
		}
	}

}
