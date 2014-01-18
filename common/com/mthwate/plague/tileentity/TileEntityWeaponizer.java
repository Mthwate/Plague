package com.mthwate.plague.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.mthwate.bookcase.TimeHelper;
import com.mthwate.plague.Plague;
import com.mthwate.plague.disease.Disease;
import com.mthwate.plague.item.ItemPlague;
import com.mthwate.plague.lib.InstrumentHelper;

public class TileEntityWeaponizer extends TileEntityBase {

	public TileEntityWeaponizer() {
		super(1);
	}

	@Override
	public void updateEntity() {
		ItemStack stack = getStackInSlot(0);
		if (stack != null) {
			if (stack.getItem().itemID == ItemPlague.diseaseVileFull.itemID) {
				ItemStack weaponStack = new ItemStack(ItemPlague.weaponizedDisease);
				weaponStack.setTagCompound(new NBTTagCompound());
				weaponStack.getTagCompound().setInteger("analyzerDuration", stack.getTagCompound().getInteger("analyzerDuration"));
				weaponStack.getTagCompound().setInteger("extractorDuration", stack.getTagCompound().getInteger("extractorDuration"));

				List<String> diseaseNames = new ArrayList<String>();
				for (Disease disease : InstrumentHelper.getDiseases(stack)) {
					diseaseNames.add(disease.getUnlocalizedName());
				}

				String diseaseName = diseaseNames.get(Plague.rand.nextInt(diseaseNames.size()));
				weaponStack.getTagCompound().setString("disease", diseaseName);

				setInventorySlotContents(0, weaponStack);

			} else if (stack.getItem().itemID == ItemPlague.weaponizedDisease.itemID) {
				if (stack.getTagCompound().getInteger("weaponizerDuration") < TimeHelper.mcToTick(100, 0, 0)) {
					stack.getTagCompound().setInteger("weaponizerDuration", stack.getTagCompound().getInteger("weaponizerDuration") + 1);

					double analyzerDuration = stack.getTagCompound().getInteger("analyzerDuration");
					double extractorDration = stack.getTagCompound().getInteger("extractorDuration");
					double weaponizerDuration = stack.getTagCompound().getInteger("weaponizerDuration");
					double effectiveness = analyzerDuration * extractorDration * weaponizerDuration / Math.pow(TimeHelper.mcToTick(100, 0, 0), 3) * 100;
					stack.getTagCompound().setDouble("effectiveness", effectiveness);
				}
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		if (slot == 0 && itemStack.itemID == ItemPlague.diseaseVileFull.itemID) {
			return true;
		}
		return false;
	}
}