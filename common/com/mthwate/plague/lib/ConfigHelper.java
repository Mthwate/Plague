package com.mthwate.plague.lib;

import net.minecraftforge.common.Configuration;

import com.mthwate.plague.Plague;
import com.mthwate.plague.block.BlockPlague;
import com.mthwate.plague.item.ItemPlague;

public class ConfigHelper {
	
	public static void diseases(Configuration config) {
		Plague.enabledDiseases.put("rabies", config.get("Diseases", "Rabies", true).getBoolean(true));
		Plague.enabledDiseases.put("crepis", config.get("Diseases", "Crepis", true).getBoolean(true));
		Plague.enabledDiseases.put("zVirus", config.get("Diseases", "ZVirus", true).getBoolean(true));
		Plague.enabledDiseases.put("endt", config.get("Diseases", "Endt", true).getBoolean(true));
		Plague.enabledDiseases.put("vampyra", config.get("Diseases", "Vampyra", true).getBoolean(true));
	}
	
	public static void blocks(Configuration config) {
		BlockPlague.extractorId = config.getBlock("Extractor", 2790).getInt();
		BlockPlague.analyzerId = config.getBlock("Analyzer", 2791).getInt();
		BlockPlague.processorId = config.getBlock("Processor", 2792).getInt();
		BlockPlague.weaponizerId = config.getBlock("Weaponizer", 2793).getInt();
		BlockPlague.boilerId = config.getBlock("Boiler", 2794).getInt();
		BlockPlague.extractorElectricT1Id = config.getBlock("Electric Extractor Tier 1", 2795).getInt();
		BlockPlague.analyzerElectricT1Id = config.getBlock("Electric Analyzer Tier 1", 2796).getInt();
		BlockPlague.extractorElectricT2Id = config.getBlock("Electric Extractor Tier 2", 2797).getInt();
		BlockPlague.analyzerElectricT2Id = config.getBlock("Electric Analyzer Tier 2", 2798).getInt();
	}
	
	public static void items(Configuration config) {
		ItemPlague.syringeEmptyId = config.getItem("Syringe", 3890).getInt();
		ItemPlague.syringeFullId = config.getItem("FilledSyringe", 3891).getInt();
		ItemPlague.diseaseVileEmptyId = config.getItem("Vile", 3892).getInt();
		ItemPlague.diseaseVileFullId = config.getItem("FilledVile", 3893).getInt();
		ItemPlague.cureId = config.getItem("Cure", 3894).getInt();
		ItemPlague.weaponizedDiseaseId = config.getItem("WeaponizedDisease", 3895).getInt();
	}
	
}
