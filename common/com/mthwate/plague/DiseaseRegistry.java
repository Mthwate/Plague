package com.mthwate.plague;

import com.mthwate.plague.disease.Disease;

public class DiseaseRegistry {

	static void addDisease(Disease disease) {
		if (Plague.enabledDiseases.get(disease.getUnlocalizedName())) {
			Plague.diseases.add(disease);
		}
	}

}
