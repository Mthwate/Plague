package com.lordxarus.plague;

import com.lordxarus.plague.disease.Disease;

public class DiseaseRegistry {
	
	static void addDisease(Disease disease, String name) {
		if (Plague.enabledDiseases.get(disease.getUnlocalizedName())) {
			Plague.diseases.add(disease);
			disease.setName(name);
		}
	}

}
