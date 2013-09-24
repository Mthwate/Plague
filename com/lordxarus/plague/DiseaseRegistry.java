package com.lordxarus.plague;

import com.lordxarus.plague.disease.Disease;

public class DiseaseRegistry {
	
	static void addDisease(Disease disease) {
		Plague.diseases.add(disease);
	}

}
