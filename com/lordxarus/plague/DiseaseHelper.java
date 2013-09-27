package com.lordxarus.plague;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.Entity;

import com.lordxarus.plague.disease.Disease;

public class DiseaseHelper {
	
	public static void addDisease(Entity entity, Disease disease) {
		entity.getEntityData().setInteger(Plague.modid + ".disease." + disease.unlocalizedName, 1);
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
		List<Disease> ret = new ArrayList();
		String all = entity.getEntityData().toString();
		all = all.replace("ForgeData:","").replace("[","").replace("]","");
		List<String> list = new ArrayList<String>(Arrays.asList(all.split(",")));
		for (String i : list) {
			if (i.contains(Plague.modid + ".disease.")) {
				int index = i.indexOf(":");
				String b = i.substring((Plague.modid + ".disease.").length(), index);
				if (Integer.parseInt(i.substring(index + 1)) > 0) {
					for(Disease disease : Plague.diseases) {
						if(disease.unlocalizedName.equals(b)) {
							ret.add(disease);
						}
					}
				}
			}
		}
		return(ret);
	}
	
	static void count(Entity entity) {
		List<Disease> diseases = getActiveDiseases(entity);
		for(Disease disease : diseases) {
			setDiseaseDuration(entity, disease, getDiseaseDuration(entity, disease) + 1);
		}
	}
	
}
