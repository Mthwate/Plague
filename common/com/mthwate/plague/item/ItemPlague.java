package com.mthwate.plague.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemPlague {

	// items
	public static Item syringeFull;
	public static Item syringeEmpty;
	public static Item diseaseVileFull;
	public static Item diseaseVileEmpty;
	public static Item cure;
	public static Item weaponizedDisease;

	// item ids
	public static int syringeFullId;
	public static int syringeEmptyId;
	public static int diseaseVileFullId;
	public static int diseaseVileEmptyId;
	public static int cureId;
	public static int weaponizedDiseaseId;

	public static void register() {
		syringeEmpty = registerItem(syringeEmpty, new ItemSyringeEmpty(syringeEmptyId), "syringeEmpty");
		syringeFull = registerItem(syringeFull, new ItemSyringeFull(syringeFullId), "syringeFull");
		diseaseVileEmpty = registerItem(diseaseVileEmpty, new ItemDiseaseVileEmpty(diseaseVileEmptyId), "diseaseVileEmpty");
		diseaseVileFull = registerItem(diseaseVileFull, new ItemDiseaseVileFull(diseaseVileFullId), "diseaseVileFull");
		cure = registerItem(cure, new ItemCure(cureId), "cure");
		weaponizedDisease = registerItem(weaponizedDisease, new ItemWeaponizedDisease(weaponizedDiseaseId), "weaponizedDisease");

		registerRecipes();
	}

	static Item registerItem(Item item, Item itemNew, String unlocalizedName) {
		item = itemNew.setUnlocalizedName(unlocalizedName);
		GameRegistry.registerItem(item, unlocalizedName);
		return item;
	}

	static void registerRecipes() {
		GameRegistry.addRecipe(new ItemStack(syringeEmpty), "g", "g", "g", 'g', Block.glass);

		GameRegistry.addRecipe(new ItemStack(diseaseVileEmpty), "w", "g", "i", 'w', Block.planks, 'g', Block.glass, 'i', Item.ingotIron);
	}

}
