package com.lordxarus.plague;

import com.lordxarus.plague.disease.Disease;

public class DiseaseRegistry {
	
	static void addDisease(Disease disease) {
		if (Plague.enabledDiseases.get(disease.getUnlocalizedName())) {
			Plague.diseases.add(disease);
			disease.setName("disease." + disease.getUnlocalizedName() + ".name");
		}
	}

}
