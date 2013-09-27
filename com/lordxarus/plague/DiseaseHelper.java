package com.lordxarus.plague;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.player.EntityPlayer;

import com.lordxarus.plague.disease.Disease;

public class DiseaseHelper {
	
	public static void addDisease(Entity entity, Disease disease) {
		if (!isDiseaseActive(entity, disease)) {
			entity.getEntityData().setInteger(Plague.modid + ".disease." + disease.unlocalizedName, 1);
		}
	}
	
	public static boolean isDiseaseActive(Entity entity, Disease disease) {
		return(entity.getEntityData().getInteger(Plague.modid + ".disease." + disease.unlocalizedName) > 0);
	}
	
	public static void setDiseaseDuration(Entity entity, Disease disease, int duration) {
		entity.getEntityData().setInteger(Plague.modid + ".disease." + disease.unlocalizedName, duration);
	}
	
	public static int getDiseaseDuration(Entity entity, Disease disease) {
		return(entity.getEntityData().getInteger(Plague.modid + ".disease." + disease.unlocalizedName));
	}
	
	public static List<Disease> getActiveDiseases(Entity entity) {
		List<Disease> active = new ArrayList();
		for(Disease disease : Plague.diseases) {
			if (isDiseaseActive(entity, disease)) {
				active.add(disease);
			}
		}
		return(active);
	}
	
	static void count(Entity entity) {
		List<Disease> diseases = getActiveDiseases(entity);
		for(Disease disease : diseases) {
			setDiseaseDuration(entity, disease, getDiseaseDuration(entity, disease) + 1);
		}
	}
	
}
