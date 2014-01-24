package com.mthwate.plague.item;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.mthwate.plague.DamageSourcePlague;
import com.mthwate.plague.Plague;
import com.mthwate.plague.disease.Disease;
import com.mthwate.plague.lib.DiseaseHelper;
import com.mthwate.plague.lib.InstrumentHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCure extends ItemBase {

	public ItemCure(int id) {
		super(id);
		maxStackSize = 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
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

	void cure(ItemStack itemStack, EntityLivingBase entity) {
		if (itemStack.getTagCompound() != null) {
			String displayDisease = itemStack.getTagCompound().getString("displayDisease");
			String cureDisease = itemStack.getTagCompound().getString("cureDisease");
	
			if (displayDisease.equals(cureDisease)) {
				for (Disease disease : Plague.diseases) {
					if (disease.getUnlocalizedName().equals(cureDisease)) {
						DiseaseHelper.setDiseaseDuration(entity, disease, -1);
					}
				}
			} else {
				int index = Plague.rand.nextInt(Plague.diseases.size());
				DiseaseHelper.addDisease(entity, Plague.diseases.get(index));
			}
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	@Override
	public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player) {
		return this.onUse(itemStack, player, player);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 32;
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer player, EntityLivingBase entity) {
		player.inventory.addItemStackToInventory(this.onUse(itemStack, player, entity));
		return true;
	}
	
	public ItemStack onUse(ItemStack itemStack, EntityPlayer player, EntityLivingBase entity) {
		--itemStack.stackSize;
		
		ItemStack itemStackSyringeEmpty = new ItemStack(ItemPlague.syringeEmpty);
		itemStackSyringeEmpty.setTagCompound(new NBTTagCompound());
		
		List<Disease> diseases = DiseaseHelper.getActiveDiseases(entity);
		if (diseases != null) {
			for(Disease disease : diseases) {
				InstrumentHelper.addRemnants(itemStackSyringeEmpty, disease);
			}
		}

		cure(itemStack, entity);

		entity.attackEntityFrom(DamageSourcePlague.syringe, 1);
		
		if (itemStack.stackSize <= 0) {
			return itemStackSyringeEmpty;
		}
		
		player.inventory.addItemStackToInventory(itemStackSyringeEmpty);
		return itemStack;
	}

}
