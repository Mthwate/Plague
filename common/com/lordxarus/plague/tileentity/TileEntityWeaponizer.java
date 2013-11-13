package com.lordxarus.plague.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.lordxarus.plague.Plague;
import com.lordxarus.plague.block.BlockPlague;
import com.lordxarus.plague.disease.Disease;
import com.lordxarus.plague.item.ItemPlague;
import com.mthwate.bookcase.Rand;
import com.mthwate.bookcase.TimeHelper;

public class TileEntityWeaponizer extends TileEntityBase {

	public TileEntityWeaponizer() {
		super(BlockPlague.analyzer, 2);
	}
	
	@Override
	public void updateEntity() {
		ItemStack stack = this.getStackInSlot(0);
		if (stack != null) {
			if (stack.getItem().itemID == ItemPlague.diseaseVileFull.itemID) {
				ItemStack weaponStack = new ItemStack(ItemPlague.weaponizedDisease);
				weaponStack.setTagCompound(new NBTTagCompound());
				weaponStack.getTagCompound().setInteger("analyzerDuration", stack.getTagCompound().getInteger("analyzerDuration"));
				weaponStack.getTagCompound().setInteger("extractorDuration", stack.getTagCompound().getInteger("extractorDuration"));
				
				List<String> diseaseNames = new ArrayList<String>();
				for (Disease disease : Plague.diseases) {
					if (stack.getTagCompound().getBoolean(disease.getUnlocalizedName())) {
						diseaseNames.add(disease.getUnlocalizedName());
					}
				}
				
				String diseaseName = diseaseNames.get(Rand.nextInt(diseaseNames.size()));
				weaponStack.getTagCompound().setString("disease", diseaseName);
				
				setInventorySlotContents(0, weaponStack);
				
			} else if (stack.getItem().itemID == ItemPlague.weaponizedDisease.itemID) {
				if (stack.getTagCompound().getInteger("weaponizerDuration") < TimeHelper.mcToTick(100, 0, 0)) {
					stack.getTagCompound().setInteger("weaponizerDuration", stack.getTagCompound().getInteger("weaponizerDuration") + 1);
					
					double analyzerDuration = stack.getTagCompound().getInteger("analyzerDuration");
					double extractorDration = stack.getTagCompound().getInteger("extractorDuration");
					double weaponizerDuration = stack.getTagCompound().getInteger("weaponizerDuration");
					double effectiveness = (analyzerDuration * extractorDration * weaponizerDuration)/(Math.pow(TimeHelper.mcToTick(100, 0, 0), 3))*100;
					stack.getTagCompound().setDouble("effectiveness", effectiveness);
				}
			}
		}
	}
}