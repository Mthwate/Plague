package com.lordxarus.plague;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

import com.lordxarus.plague.block.BlockExtractor;
import com.lordxarus.plague.disease.Disease;
import com.lordxarus.plague.disease.DiseaseChickenpox;
import com.lordxarus.plague.disease.DiseaseMalaria;
import com.lordxarus.plague.disease.DiseaseRabies;
import com.lordxarus.plague.disease.DiseaseWestNile;
import com.lordxarus.plague.item.ItemDiseaseVileEmpty;
import com.lordxarus.plague.item.ItemDiseaseVileFull;
import com.lordxarus.plague.item.ItemSyringeEmpty;
import com.lordxarus.plague.item.ItemSyringeFull;
import com.lordxarus.plague.lib.ModLogger;
import com.lordxarus.plague.tileentity.TileEntityExtractor;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid=Plague.modid, name=Plague.modName, version=".ver.")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Plague {
	//mod
	public static final String modid = "plague";
	static final String modName = "Plague";
	
	//instance
	@Instance(modid)
	public static Plague instance;
	
	//items
	public static Item itemSyringeFull;
	public static Item itemSyringeEmpty;
	public static Item itemDiseaseVileFull;
	public static Item itemDiseaseVileEmpty;
	
	//item ids
	int itemSyringeFullId;
	int itemSyringeEmptyId;
	int itemDiseaseVileFullId;
	int itemDiseaseVileEmptyId;
	
	//blocks
	public static Block blockExtractor;
	
	//block ids
	int blockExtractorId;
	
	//a list of all the registered diseases in Plague
	public static List<Disease> diseases = new ArrayList<Disease>();
	
	//a list of all the diseases enabled in the config file
	public static Map<String, Boolean> enabledDiseases = new HashMap<String, Boolean>();
	
	//diseases
	public static Disease diseaseRabies;
	public static Disease diseaseWestNile;
	public static Disease diseaseMalaria;
	public static Disease diseaseChickenpox;
	
	//settings
	public static boolean verbose;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//enable logging
		ModLogger.init();
		
		//configuration
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		//items
		itemSyringeEmptyId = config.getItem("Syringe", 3790).getInt();
		itemSyringeFullId = config.getItem("FilledSyringe", 3791).getInt();
		itemDiseaseVileEmptyId = config.getItem("Vile", 3792).getInt();
		itemDiseaseVileFullId = config.getItem("FilledVile", 3793).getInt();
		
		//blocks
		blockExtractorId = config.getBlock("Extractor", 2790).getInt();
		
		//diseases
		enabledDiseases.put("rabies", config.get("Diseases", "Rabies", true).getBoolean(true));
		enabledDiseases.put("westNile", config.get("Diseases", "WestNileVirus", true).getBoolean(true));
		enabledDiseases.put("malaria", config.get("Diseases", "Malaria", true).getBoolean(true));
		enabledDiseases.put("chickenpox", config.get("Diseases", "Chickenpox", true).getBoolean(true));
		
		//settings
		verbose = config.get("Settings", "Verbose", false).getBoolean(false);
		
		config.save();
		
		//event hooks
		MinecraftForge.EVENT_BUS.register(new EventHooksPlague());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		//gui handler
		
		NetworkRegistry.instance().registerGuiHandler(Plague.instance, new GuiHandler());
		
		//tile entities
		
		GameRegistry.registerTileEntity(TileEntityExtractor.class, "plagueExtractor");

		//items
		
		itemSyringeEmpty = (new ItemSyringeEmpty(itemSyringeEmptyId)).setUnlocalizedName("syringeEmpty");
		GameRegistry.registerItem(itemSyringeEmpty, "syringeEmpty");
		LanguageRegistry.addName(itemSyringeEmpty, "Syringe");
		GameRegistry.addRecipe(new ItemStack(itemSyringeEmpty), "g", "g", "g",
				'g', Block.glass);

		itemSyringeFull = (new ItemSyringeFull(itemSyringeFullId)).setUnlocalizedName("syringeFull");
		GameRegistry.registerItem(itemSyringeFull, "syringeFull");
		LanguageRegistry.addName(itemSyringeFull, "Filled Syringe");

		itemDiseaseVileEmpty = (new ItemDiseaseVileEmpty(itemDiseaseVileEmptyId)).setUnlocalizedName("diseaseVileEmpty");
		GameRegistry.registerItem(itemDiseaseVileEmpty, "diseaseVileEmpty");
		LanguageRegistry.addName(itemDiseaseVileEmpty, "Disease Vile");
		GameRegistry.addRecipe(new ItemStack(itemDiseaseVileEmpty), "w", "g", "i",
				'w', Block.wood, 'g', Block.glass ,'i', Item.ingotIron);

		itemDiseaseVileFull = (new ItemDiseaseVileFull(itemDiseaseVileFullId)).setUnlocalizedName("diseaseVileFull");
		GameRegistry.registerItem(itemDiseaseVileFull, "diseaseVileFull");
		LanguageRegistry.addName(itemDiseaseVileFull, "Disease Filled Vile");

		//blocks
		
		blockExtractor = (new BlockExtractor(blockExtractorId)).setUnlocalizedName("extractor");
		GameRegistry.registerBlock(blockExtractor, "extractor");
		LanguageRegistry.addName(blockExtractor, "Extractor");
		
		//diseases
		
		diseaseRabies = (new DiseaseRabies().setUnlocalizedName("rabies"));
		DiseaseRegistry.addDisease(diseaseRabies, "Rabies");
		
		diseaseWestNile = (new DiseaseWestNile().setUnlocalizedName("westNile"));
		DiseaseRegistry.addDisease(diseaseWestNile, "West Nile Virus");
		
		diseaseMalaria = (new DiseaseMalaria().setUnlocalizedName("malaria"));
		DiseaseRegistry.addDisease(diseaseMalaria, "Malaria");
		
		diseaseChickenpox = (new DiseaseChickenpox().setUnlocalizedName("chickenpox"));
		DiseaseRegistry.addDisease(diseaseChickenpox, "Chickenpox");
		
		//death messages
		
		LanguageRegistry.instance().addStringLocalization("death.attack.syringe", "%1$s died of blood loss.");
		LanguageRegistry.instance().addStringLocalization("death.attack.disease", "%1$s died of disease.");
		
	}
	
}