package com.mthwate.plague.lib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.mthwate.plague.Plague;
import com.mthwate.plague.disease.Disease;

public class InstrumentHelper {

	public static void addDisease(ItemStack itemStack, Disease disease) {
		checkNbt(itemStack);
		itemStack.getTagCompound().setBoolean(Plague.modid + ".disease." + disease.getUnlocalizedName(), true);
	}

	public static void removeDisease(ItemStack itemStack, Disease disease) {
		checkNbt(itemStack);
		itemStack.getTagCompound().setBoolean(Plague.modid + ".disease." + disease.getUnlocalizedName(), false);
	}

	public static boolean hasDisease(ItemStack itemStack, Disease disease) {
		checkNbt(itemStack);
		return itemStack.getTagCompound().getBoolean(Plague.modid + ".disease." + disease.getUnlocalizedName());
	}
	
	public static List<Disease> getDiseases(ItemStack itemStack) {
		List<Disease> diseases = new ArrayList<Disease>();
		for (Disease disease : Plague.diseases) {
			if (hasDisease(itemStack, disease)) {
				diseases.add(disease);
			}
		}
		return diseases;
	}

	public static void addRemnants(ItemStack itemStack, Disease disease) {
		checkNbt(itemStack);
		itemStack.getTagCompound().setBoolean(Plague.modid + ".remnants." + disease.getUnlocalizedName(), true);
	}

	public static void removeRemnants(ItemStack itemStack, Disease disease) {
		checkNbt(itemStack);
		itemStack.getTagCompound().setBoolean(Plague.modid + ".remnants." + disease.getUnlocalizedName(), false);
	}

	public static boolean hasRemnants(ItemStack itemStack, Disease disease) {
		checkNbt(itemStack);
		return itemStack.getTagCompound().getBoolean(Plague.modid + ".remnants." + disease.getUnlocalizedName());
	}
	
	public static List<Disease> getRemnants(ItemStack itemStack) {
		List<Disease> diseases = new ArrayList<Disease>();
		for (Disease disease : Plague.diseases) {
			if (hasRemnants(itemStack, disease)) {
				diseases.add(disease);
			}
		}
		return diseases;
	}
	
	public static void checkNbt(ItemStack itemStack) {
		if (itemStack.getTagCompound() == null) {
			itemStack.setTagCompound(new NBTTagCompound());
		}
	}
	
}
