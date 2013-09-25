package com.lordxarus.plague;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

import com.lordxarus.plague.disease.Disease;
import com.lordxarus.plague.disease.DiseaseRabies;
import com.lordxarus.plague.disease.DiseaseWestNile;
import com.lordxarus.plague.item.ItemSyringeEmpty;
import com.lordxarus.plague.item.ItemSyringeFull;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid=Plague.modid, name=Plague.modName, version="0.1")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Plague {
	//mod
	public static final String modid = "plague";
	static final String modName = "Plague";
	
	//items
	public static Item itemSyringeFull;
	public static Item itemSyringeEmpty;
	
	//item ids
	int itemSyringeFullId;
	int itemSyringeEmptyId;
	
	//diseases
	static List<Disease> diseases = new ArrayList<Disease>();
	Disease diseaseRabies;
	Disease diseaseWestNile;
	
	//settings
	static boolean verbose;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//enable logging
		ModLogger.init();
		
		//configuration
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		//items
		itemSyringeEmptyId = config.getItem("Syringe", 3790).getInt();
		itemSyringeFullId = config.getItem("Filled Syringe", 3791).getInt();
		
		//settings
		verbose = config.get("Settings", "Verbose", false).getBoolean(false);
		
		config.save();
		
		//event hooks
		MinecraftForge.EVENT_BUS.register(new EventHooksPlague());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {

		//items
		
		itemSyringeEmpty = (new ItemSyringeEmpty(itemSyringeEmptyId)).setUnlocalizedName("syringeEmpty");
		LanguageRegistry.addName(itemSyringeEmpty, "Syringe");
		GameRegistry.addRecipe(new ItemStack(itemSyringeEmpty), "x", "x", "x",
				'x', Block.glass);

		itemSyringeFull = (new ItemSyringeFull(itemSyringeFullId)).setUnlocalizedName("syringeFull");
		LanguageRegistry.addName(itemSyringeFull, "Filled Syringe");
		
		//diseases
		
		diseaseRabies = (new DiseaseRabies().setName("Rabies"));
		DiseaseRegistry.addDisease(diseaseRabies);
		
		diseaseWestNile = (new DiseaseWestNile().setName("West Nile"));
		DiseaseRegistry.addDisease(diseaseWestNile);
	}
	
	
}