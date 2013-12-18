package com.mthwate.plague.tileentity;

import net.minecraft.item.ItemStack;

import com.mthwate.bookcase.TimeHelper;
import com.mthwate.plague.block.BlockPlague;
import com.mthwate.plague.item.ItemPlague;

public class TileEntityAnalyzer extends TileEntityBase {

	public TileEntityAnalyzer() {
		super(BlockPlague.analyzer, 1);
	}

	@Override
	public void updateEntity() {
		ItemStack stack = getStackInSlot(0);
		if (stack != null) {
			if (stack.getItem().itemID == ItemPlague.diseaseVileFull.itemID && stack.getTagCompound() != null) {
				if (stack.getTagCompound().getInteger("analyzerDuration") < TimeHelper.mcToTick(100, 0, 0)) {
					stack.getTagCompound().setInteger("analyzerDuration", stack.getTagCompound().getInteger("analyzerDuration") + 1);
				}
			}
		}
	}
}
