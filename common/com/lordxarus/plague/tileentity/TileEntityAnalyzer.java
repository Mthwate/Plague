package com.lordxarus.plague.tileentity;

import net.minecraft.item.ItemStack;

import com.lordxarus.plague.block.BlockPlague;
import com.lordxarus.plague.item.ItemPlague;
import com.mthwate.bookcase.TimeHelper;

public class TileEntityAnalyzer extends TileEntityBase {

	public TileEntityAnalyzer() {
		super(BlockPlague.analyzer, 1);
	}

	@Override
	public void updateEntity() {
		ItemStack stack = getStackInSlot(0);
		if (stack != null) {
			if (stack.getItem().itemID == ItemPlague.diseaseVileFull.itemID) {
				if (stack.getTagCompound().getInteger("analyzerDuration") < TimeHelper.mcToTick(100, 0, 0)) {
					stack.getTagCompound().setInteger("analyzerDuration", stack.getTagCompound().getInteger("analyzerDuration") + 1);
				}
			}
		}
	}
}
