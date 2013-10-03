package com.lordxarus.plague;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

import com.lordxarus.plague.block.BlockExtractor;
import com.lordxarus.plague.disease.Disease;
import com.lordxarus.plague.disease.DiseaseMalaria;
import com.lordxarus.plague.disease.DiseaseRabies;
import com.lordxarus.plague.disease.DiseaseWestNile;
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
	
	//item ids
	int itemSyringeFullId;
	int itemSyringeEmptyId;
	
	//blocks
	public static Block blockExtractor;
	
	//block ids
	int blockExtractorId;
	
	//diseases
	// Creates empty list of diseases (individual diseases added in disease class)
	public static List<Disease> diseases = new ArrayList<Disease>();
	public static Disease diseaseRabies;
	public static Disease diseaseWestNile;
	public static Disease diseaseMalaria;
	
	//settings
	public static boolean verbose;
	
	//misc
	public static World world = Minecraft.getMinecraft().theWorld;
	
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
		
		//blocks
		blockExtractorId = config.getBlock("Extractor", 2790).getInt();
		
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
		GameRegistry.addRecipe(new ItemStack(itemSyringeEmpty), "x", "x", "x",
				'x', Block.glass);

		itemSyringeFull = (new ItemSyringeFull(itemSyringeFullId)).setUnlocalizedName("syringeFull");
		GameRegistry.registerItem(itemSyringeFull, "syringeFull");
		LanguageRegistry.addName(itemSyringeFull, "Filled Syringe");

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
	}
	
	
}