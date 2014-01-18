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

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		if (slot == 0 && itemStack.itemID == ItemPlague.diseaseVileFull.itemID) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		if (itemStack.itemID == ItemPlague.diseaseVileFull.itemID && itemStack.getTagCompound() != null) {
			return this.getOutDirection(slot) == side && itemStack.getTagCompound().getInteger("analyzerDuration") / (60 * 60) >= this.getOutPercent();
		}
		return this.getOutDirection(slot) == side;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {0};
	}
}
