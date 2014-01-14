package com.mthwate.plague.tileentity;

import net.minecraft.item.ItemStack;

import com.mthwate.bookcase.TimeHelper;
import com.mthwate.plague.item.ItemPlague;

public class TileEntityAnalyzerElectric extends TileEntityBaseElectric {

	public TileEntityAnalyzerElectric() {
		super(1);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if(!this.worldObj.isRemote) {
		
			ItemStack stack = getStackInSlot(0);
			if (stack != null) {
				if (stack.getItem().itemID == ItemPlague.diseaseVileFull.itemID && stack.getTagCompound() != null && this.getEnergy(null) >= this.getEnergyUsage()) {
					
					if (stack.getTagCompound().getInteger("analyzerDuration") < TimeHelper.mcToTick(100, 0, 0)) {
						stack.getTagCompound().setInteger("analyzerDuration", stack.getTagCompound().getInteger("analyzerDuration") + (int) Math.pow(2, this.getTier()));
					}
					
					if (stack.getTagCompound().getInteger("analyzerDuration") > TimeHelper.mcToTick(100, 0, 0)) {
						stack.getTagCompound().setInteger("analyzerDuration", (int) TimeHelper.mcToTick(100, 0, 0));
					}
					
					this.modifyEnergyStored(-this.getEnergyUsage());
					
				}
			}
		}
	}
}
