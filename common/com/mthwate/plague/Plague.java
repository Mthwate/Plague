package com.mthwate.plague;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

import com.mthwate.bookcase.LangHelper;
import com.mthwate.bookcase.ModLogger;
import com.mthwate.plague.block.BlockPlague;
import com.mthwate.plague.command.CommandContract;
import com.mthwate.plague.command.CommandCure;
import com.mthwate.plague.disease.Disease;
import com.mthwate.plague.disease.DiseaseChickenpox;
import com.mthwate.plague.disease.DiseaseCrepis;
import com.mthwate.plague.disease.DiseaseEndt;
import com.mthwate.plague.disease.DiseaseMalaria;
import com.mthwate.plague.disease.DiseaseRabies;
import com.mthwate.plague.disease.DiseaseWestNile;
import com.mthwate.plague.disease.DiseaseZVirus;
import com.mthwate.plague.entity.EntityWeaponizedDisease;
import com.mthwate.plague.item.ItemPlague;
import com.mthwate.plague.lib.ConfigHelper;
import com.mthwate.plague.proxy.CommonProxy;
import com.mthwate.plague.tileentity.TileEntityAnalyzer;
import com.mthwate.plague.tileentity.TileEntityBoiler;
import com.mthwate.plague.tileentity.TileEntityExtractor;
import com.mthwate.plague.tileentity.TileEntityProcessor;
import com.mthwate.plague.tileentity.TileEntityWeaponizer;

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
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {"PlagueParticle"}, packetHandler = PacketHandler.class)
public class Plague {
	// mod
	public static final String modid = "plague";
	static final String modName = "Plague";

	// instance
	@Instance(modid)
	public static Plague instance;

	// a list of all the enabled diseases in Plague
	public static List<Disease> diseases = new ArrayList<Disease>();

	// a list of all the disease names and if they are enabled in the config file
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
	
	// proxy
	@SidedProxy(clientSide="com.mthwate.plague.proxy.ClientProxy", serverSide="com.mthwate.plague.proxy.CommonProxy")
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
		GameRegistry.registerTileEntity(TileEntityBoiler.class, "plagueBoiler");

		// registers items
		ItemPlague.register();

		// registers blocks
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

		// entities
		proxy.registerEntityRenderingHandlers();
		EntityRegistry.registerModEntity(EntityWeaponizedDisease.class, "weaponizedDisease", 1, this, 80, 1, true);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		// configuration
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		// items
		ConfigHelper.items(config);

		// blocks
		ConfigHelper.blocks(config);

		// diseases
		ConfigHelper.diseases(config);

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
		// server commands
		event.registerServerCommand(new CommandContract());
		event.registerServerCommand(new CommandCure());
	}

}
