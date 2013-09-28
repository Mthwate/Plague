package com.lordxarus.plague;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.entity.Entity;

import com.lordxarus.plague.disease.Disease;

public class DiseaseHelper {
	
	public static void addDisease(Entity entity, Disease disease) {
		if (!isDiseaseActive(entity, disease)) {
			entity.getEntityData().setInteger(Plague.modid + ".disease." + disease.getUnlocalizedName(), 1);
			ModLogger.log(Level.INFO, disease.getUnlocalizedName() + " added to " + entity.getEntityName() + ".", true);
		}
	}
	
	public static boolean isDiseaseActive(Entity entity, Disease disease) {
		return(entity.getEntityData().getInteger(Plague.modid + ".disease." + disease.getUnlocalizedName()) > 0);
	}
	
	public static void setDiseaseDuration(Entity entity, Disease disease, int duration) {
		entity.getEntityData().setInteger(Plague.modid + ".disease." + disease.getUnlocalizedName(), duration);
	}
	
	public static int getDiseaseDuration(Entity entity, Disease disease) {
		return(entity.getEntityData().getInteger(Plague.modid + ".disease." + disease.getUnlocalizedName()));
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
