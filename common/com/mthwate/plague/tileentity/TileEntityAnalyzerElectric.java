package com.mthwate.plague.tileentity;

import net.minecraft.item.ItemStack;

import com.mthwate.bookcase.TimeHelper;
import com.mthwate.plague.block.BlockPlague;
import com.mthwate.plague.item.ItemPlague;

public class TileEntityAnalyzerElectric extends TileEntityBaseElectric {

	public TileEntityAnalyzerElectric() {
		super(BlockPlague.analyzerElectric, 1);
	}

	@Override
	public void updateEntity() {
		ItemStack stack = getStackInSlot(0);
		if (stack != null) {
			if (stack.getItem().itemID == ItemPlague.diseaseVileFull.itemID && stack.getTagCompound() != null && this.getEnergyStored() >= 1) {
				
				if (stack.getTagCompound().getInteger("analyzerDuration") < TimeHelper.mcToTick(100, 0, 0)) {
					stack.getTagCompound().setInteger("analyzerDuration", stack.getTagCompound().getInteger("analyzerDuration") + 2);
				}
				
				if (stack.getTagCompound().getInteger("analyzerDuration") > TimeHelper.mcToTick(100, 0, 0)) {
					stack.getTagCompound().setInteger("analyzerDuration", (int) TimeHelper.mcToTick(100, 0, 0));
				}
				
				this.setEnergyStored(this.getEnergyStored() - 1);
				
			}
		}
	}
}
