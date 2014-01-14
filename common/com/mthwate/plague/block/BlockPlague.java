package com.mthwate.plague.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.mthwate.plague.item.ItemPlague;

import cpw.mods.fml.common.registry.GameRegistry;

public class BlockPlague {

	// blocks
	public static Block extractor;
	public static Block analyzer;
	public static Block processor;
	public static Block weaponizer;
	public static Block boiler;
	public static Block extractorElectricT1;
	public static Block analyzerElectricT1;
	public static Block extractorElectricT2;
	public static Block analyzerElectricT2;

	// block ids
	public static int extractorId;
	public static int analyzerId;
	public static int processorId;
	public static int weaponizerId;
	public static int boilerId;
	public static int extractorElectricT1Id;
	public static int analyzerElectricT1Id;
	public static int extractorElectricT2Id;
	public static int analyzerElectricT2Id;

	public static void register() {
		extractor = registerBlock(extractor, new BlockExtractor(extractorId), "extractor");
		analyzer = registerBlock(analyzer, new BlockAnalyzer(analyzerId), "analyzer");
		processor = registerBlock(processor, new BlockProcessor(processorId), "processor");
		weaponizer = registerBlock(weaponizer, new BlockWeaponizer(weaponizerId), "weaponizer");
		boiler = registerBlock(boiler, new BlockBoiler(boilerId), "boiler");
		extractorElectricT1 = registerBlock(extractorElectricT1, new BlockExtractorElectric(extractorElectricT1Id, 1), "extractorElectricT1");
		analyzerElectricT1 = registerBlock(analyzerElectricT1, new BlockAnalyzerElectric(analyzerElectricT1Id, 1), "analyzerElectricT1");
		extractorElectricT2 = registerBlock(extractorElectricT2, new BlockExtractorElectric(extractorElectricT2Id, 2), "extractorElectricT2");
		analyzerElectricT2 = registerBlock(analyzerElectricT2, new BlockAnalyzerElectric(analyzerElectricT2Id, 2), "analyzerElectricT2");

		registerRecipes();
	}

	static Block registerBlock(Block block, Block blockNew, String unlocalizedName) {
		block = blockNew.setUnlocalizedName(unlocalizedName);
		GameRegistry.registerBlock(block, unlocalizedName);
		return block;
	}

	static void registerRecipes() {
		GameRegistry.addRecipe(new ItemStack(extractor), "ppp", "i i", " i ", 'p', Block.planks, 'i', Item.ingotIron);

		GameRegistry.addRecipe(new ItemStack(analyzer), "pvp", "ppp", 'p', Block.planks, 'v', ItemPlague.diseaseVileEmpty);

		GameRegistry.addRecipe(new ItemStack(processor), "ppp", "i i", " g ", 'p', Block.planks, 'i', Item.ingotIron, 'g', Item.ingotGold);

		GameRegistry.addRecipe(new ItemStack(weaponizer), "iii", "iei", "iii", 'i', Item.ingotIron, 'e', Item.eyeOfEnder);

		GameRegistry.addRecipe(new ItemStack(boiler), "iii", "ifi", "isi", 'i', Item.ingotIron, 'f', Block.furnaceIdle, 's', Item.flintAndSteel);
		
		GameRegistry.addRecipe(new ItemStack(extractorElectricT1), "iti", "bmb", "iii", 'i', Item.ingotIron, 't', Block.torchRedstoneActive, 'b', Block.blockRedstone, 'm', BlockPlague.extractor);

		GameRegistry.addRecipe(new ItemStack(analyzerElectricT1), "iti", "bmb", "iii", 'i', Item.ingotIron, 't', Block.torchRedstoneActive, 'b', Block.blockRedstone, 'm', BlockPlague.analyzer);
		
		GameRegistry.addRecipe(new ItemStack(extractorElectricT2), "gtg", "mbm", "ggg", 'g', Item.ingotGold, 't', Block.torchRedstoneActive, 'b', Block.blockRedstone, 'm', BlockPlague.extractorElectricT1);

		GameRegistry.addRecipe(new ItemStack(analyzerElectricT2), "gtg", "mbm", "ggg", 'g', Item.ingotGold, 't', Block.torchRedstoneActive, 'b', Block.blockRedstone, 'm', BlockPlague.analyzerElectricT1);
	}

}
