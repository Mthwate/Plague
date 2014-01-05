package com.mthwate.plague.item;

import java.util.List;

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

public class ItemCure extends ItemBaseFood {

	public ItemCure(int id) {
		super(id, 0, 0F, false);
		maxStackSize = 1;
		setAlwaysEdible();
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

	void cure(ItemStack itemStack, EntityPlayer player) {
		if (itemStack.getTagCompound() != null) {
			String displayDisease = itemStack.getTagCompound().getString("displayDisease");
			String cureDisease = itemStack.getTagCompound().getString("cureDisease");
	
			if (displayDisease.equals(cureDisease)) {
				for (Disease disease : Plague.diseases) {
					if (disease.getUnlocalizedName().equals(cureDisease)) {
						DiseaseHelper.setDiseaseDuration(player, disease, -1);
					}
				}
			} else {
				int index = Plague.rand.nextInt(Plague.diseases.size());
				DiseaseHelper.addDisease(player, Plague.diseases.get(index));
			}
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player) {
		--itemStack.stackSize;
		
		ItemStack itemStackSyringeEmpty = new ItemStack(ItemPlague.syringeEmpty);
		itemStackSyringeEmpty.setTagCompound(new NBTTagCompound());
		
		List<Disease> diseases = DiseaseHelper.getActiveDiseases(player);
		if (diseases != null) {
			for(Disease disease : diseases) {
				InstrumentHelper.addRemnants(itemStackSyringeEmpty, disease);
			}
		}

		cure(itemStack, player);

		
		if (itemStack.stackSize <= 0) {
			player.attackEntityFrom(DamageSourcePlague.syringe, 1);
			return itemStackSyringeEmpty;
		}
		player.inventory.addItemStackToInventory(itemStackSyringeEmpty);
		player.attackEntityFrom(DamageSourcePlague.syringe, 1);
		return itemStack;
	}

}
