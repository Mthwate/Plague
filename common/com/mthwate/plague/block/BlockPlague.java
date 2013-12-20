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

	// block ids
	public static int extractorId;
	public static int analyzerId;
	public static int processorId;
	public static int weaponizerId;
	public static int boilerId;

	public static void register() {
		extractor = registerBlock(extractor, new BlockExtractor(extractorId), "extractor");
		analyzer = registerBlock(analyzer, new BlockAnalyzer(analyzerId), "analyzer");
		processor = registerBlock(processor, new BlockProcessor(processorId), "processor");
		weaponizer = registerBlock(weaponizer, new BlockWeaponizer(weaponizerId), "weaponizer");
		boiler = registerBlock(boiler, new BlockBoiler(boilerId), "boiler");

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
	}

}
