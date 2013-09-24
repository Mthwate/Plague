package com.lordxarus.plague;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.Entity;

public class DiseaseHelper {
	
	public static void addDisease(Entity entity, String disease, int duration) {
		entity.getEntityData().setInteger(Plague.modid + ".disease." + disease, duration);
	}
	
	public static boolean isDiseaseActive(Entity entity, String disease) {
		return(entity.getEntityData().getInteger(Plague.modid + ".disease." + disease) > 0);
	}
	
	public static void setDiseaseDuration(Entity entity, String disease, int duration) {
		entity.getEntityData().setInteger(Plague.modid + ".disease." + disease, duration);
	}
	
	public static int getDiseaseDuration(Entity entity, String disease) {
		return(entity.getEntityData().getInteger(Plague.modid + ".disease." + disease));
	}
	
	public static List<String> getAllDiseases(Entity entity) {
		List<String> ret = new ArrayList();
		String all = entity.getEntityData().toString();
		all = all.replace("ForgeData:","").replace("[","").replace("]","");
		List<String> list = new ArrayList<String>(Arrays.asList(all.split(",")));
		for (String i : list) {
			if (i.contains(Plague.modid + ".disease.")) {
				int index = i.indexOf(":");
				String b = i.substring((Plague.modid + ".disease.").length(), index);
				if (Integer.parseInt(i.substring(index + 1)) > 0) {
					ret.add(b);
				}
			}
		}
		return(ret);
	}
	
}
