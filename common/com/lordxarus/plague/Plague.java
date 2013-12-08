package com.lordxarus.plague;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

import com.lordxarus.plague.block.BlockPlague;
import com.lordxarus.plague.command.CommandContract;
import com.lordxarus.plague.disease.Disease;
import com.lordxarus.plague.disease.DiseaseChickenpox;
import com.lordxarus.plague.disease.DiseaseCrepis;
import com.lordxarus.plague.disease.DiseaseEndt;
import com.lordxarus.plague.disease.DiseaseMalaria;
import com.lordxarus.plague.disease.DiseaseRabies;
import com.lordxarus.plague.disease.DiseaseWestNile;
import com.lordxarus.plague.disease.DiseaseZVirus;
import com.lordxarus.plague.entity.EntityWeaponizedDisease;
import com.lordxarus.plague.item.ItemPlague;
import com.lordxarus.plague.proxy.CommonProxy;
import com.lordxarus.plague.tileentity.TileEntityAnalyzer;
import com.lordxarus.plague.tileentity.TileEntityExtractor;
import com.lordxarus.plague.tileentity.TileEntityProcessor;
import com.lordxarus.plague.tileentity.TileEntityWeaponizer;
import com.mthwate.bookcase.LangHelper;
import com.mthwate.bookcase.ModLogger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Plague.modid, name = Plague.modName, version = "@VERSION@")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Plague {
	// mod
	public static final String modid = "plague";
	static final String modName = "Plague";

	// instance
	@Instance(modid)
	public static Plague instance;

	// a list of all the registered diseases in Plague
	public static List<Disease> diseases = new ArrayList<Disease>();

	// a list of all the diseases enabled in the config file
	public static Map<String, Boolean> enabledDiseases = new HashMap<String, Boolean>();

	// diseases
	public static Disease diseaseRabies;
	public static Disease diseaseWestNile;
	public static Disease diseaseMalaria;
	public static Disease diseaseChickenpox;
	public static Disease diseaseCrepis;
	public static Disease diseaseZVirus;
	public static Disease diseaseEndt;

	// settings
	public static boolean verbose;

	// logger
	public static ModLogger logger;

	// random
	public static Random rand = new Random();
	
	@SidedProxy(clientSide="com.lordxarus.com.proxy.ClientProxy", serverSide="com.lordxarus.com.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void init(FMLInitializationEvent event) {

		// lang
		LangHelper.register(modid, "en_US");

		// gui handler

		NetworkRegistry.instance().registerGuiHandler(Plague.instance, new GuiHandler());

		// tile entities

		GameRegistry.registerTileEntity(TileEntityExtractor.class, "plagueExtractor");
		GameRegistry.registerTileEntity(TileEntityAnalyzer.class, "plagueAnalyzer");
		GameRegistry.registerTileEntity(TileEntityProcessor.class, "plagueProcessor");
		GameRegistry.registerTileEntity(TileEntityWeaponizer.class, "plagueWeaponizer");

		ItemPlague.register();

		BlockPlague.register();

		// diseases

		diseaseRabies = new DiseaseRabies().setUnlocalizedName("rabies");
		DiseaseRegistry.addDisease(diseaseRabies);

		diseaseWestNile = new DiseaseWestNile().setUnlocalizedName("westNile");
		DiseaseRegistry.addDisease(diseaseWestNile);

		diseaseMalaria = new DiseaseMalaria().setUnlocalizedName("malaria");
		DiseaseRegistry.addDisease(diseaseMalaria);

		diseaseChickenpox = new DiseaseChickenpox().setUnlocalizedName("chickenpox");
		DiseaseRegistry.addDisease(diseaseChickenpox);

		diseaseCrepis = new DiseaseCrepis().setUnlocalizedName("crepis");
		DiseaseRegistry.addDisease(diseaseCrepis);

		diseaseZVirus = new DiseaseZVirus().setUnlocalizedName("zVirus");
		DiseaseRegistry.addDisease(diseaseZVirus);

		diseaseEndt = new DiseaseEndt().setUnlocalizedName("endt");
		DiseaseRegistry.addDisease(diseaseEndt);

		proxy.registerEntityRenderingHandlers();
		EntityRegistry.registerModEntity(EntityWeaponizedDisease.class, "weaponizedDisease", 1, this, 80, 1, true);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		// configuration
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		// items
		ItemPlague.syringeEmptyId = config.getItem("Syringe", 3790).getInt();
		ItemPlague.syringeFullId = config.getItem("FilledSyringe", 3791).getInt();
		ItemPlague.diseaseVileEmptyId = config.getItem("Vile", 3792).getInt();
		ItemPlague.diseaseVileFullId = config.getItem("FilledVile", 3793).getInt();
		ItemPlague.cureId = config.getItem("Cure", 3794).getInt();
		ItemPlague.weaponizedDiseaseId = config.getItem("WeaponizedDisease", 3795).getInt();

		// blocks
		BlockPlague.extractorId = config.getBlock("Extractor", 2790).getInt();
		BlockPlague.analyzerId = config.getBlock("Analyzer", 2791).getInt();
		BlockPlague.processorId = config.getBlock("Processor", 2792).getInt();
		BlockPlague.weaponizerId = config.getBlock("Weaponizer", 2793).getInt();

		// diseases
		enabledDiseases.put("rabies", config.get("Diseases", "Rabies", true).getBoolean(true));
		enabledDiseases.put("westNile", config.get("Diseases", "WestNileVirus", true).getBoolean(true));
		enabledDiseases.put("malaria", config.get("Diseases", "Malaria", true).getBoolean(true));
		enabledDiseases.put("chickenpox", config.get("Diseases", "Chickenpox", true).getBoolean(true));
		enabledDiseases.put("crepis", config.get("Diseases", "Crepis", true).getBoolean(true));
		enabledDiseases.put("zVirus", config.get("Diseases", "ZVirus", true).getBoolean(true));
		enabledDiseases.put("endt", config.get("Diseases", "Endt", true).getBoolean(true));

		// settings
		verbose = config.get("Settings", "Verbose", false).getBoolean(false);

		config.save();

		// event hooks
		MinecraftForge.EVENT_BUS.register(new EventHooksPlague());

		// enable logging
		logger = new ModLogger(modid, verbose);
	}

	@EventHandler
	public void serverStart(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandContract());
	}

}
