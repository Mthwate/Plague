package com.mthwate.plague.tileentity;

import net.minecraft.item.ItemStack;

import com.mthwate.bookcase.TimeHelper;
import com.mthwate.plague.item.ItemPlague;

public class TileEntityAnalyzer extends TileEntityBase {

	public TileEntityAnalyzer() {
		super(1);
	}

	@Override
	public void updateEntity() {
		ItemStack stack = getStackInSlot(0);
		if (stack != null) {
			if (stack.itemID == ItemPlague.diseaseVileFull.itemID && stack.getTagCompound() != null) {
				if (stack.getTagCompound().getInteger("analyzerDuration") < TimeHelper.mcToTick(100, 0, 0)) {
					stack.getTagCompound().setInteger("analyzerDuration", stack.getTagCompound().getInteger("analyzerDuration") + 1);
				}
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		if (slot == 1 && itemStack.itemID == ItemPlague.diseaseVileFull.itemID) {
			return true;
		}
		return false;
	}
}
