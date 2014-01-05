package com.mthwate.plague.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.mthwate.bookcase.TimeHelper;
import com.mthwate.plague.block.BlockPlague;
import com.mthwate.plague.disease.Disease;
import com.mthwate.plague.item.ItemPlague;
import com.mthwate.plague.lib.InstrumentHelper;

public class TileEntityExtractorElectric extends TileEntityBaseElectric {

	public TileEntityExtractorElectric() {
		super(BlockPlague.extractorElectric, 2);
	}

	@Override
	public void updateEntity() {

		// gets the item stacks in all inventory slots
		ItemStack stackZero = getStackInSlot(0);
		ItemStack stackOne = getStackInSlot(1);

		if (stackZero != null && stackOne != null) {

			if (stackZero.getItem().itemID == ItemPlague.syringeFull.itemID && stackOne.getItem().itemID == ItemPlague.diseaseVileEmpty.itemID && stackZero.getTagCompound() != null) {
				
				ItemStack syringeStack = new ItemStack(ItemPlague.syringeEmpty);
				syringeStack.setTagCompound(new NBTTagCompound());

				ItemStack vileStack = new ItemStack(ItemPlague.diseaseVileFull);
				vileStack.setTagCompound(new NBTTagCompound());
				vileStack.getTagCompound().setBoolean("complete", false);

				for (Disease disease : InstrumentHelper.getDiseases(stackZero)) {
					InstrumentHelper.addDisease(vileStack, disease);
					InstrumentHelper.addRemnants(syringeStack, disease);
				}
				
				setInventorySlotContents(0, syringeStack);
				setInventorySlotContents(1, vileStack);
			}
		}

		if (stackOne != null) {
			if (stackOne.getItem().itemID == ItemPlague.diseaseVileFull.itemID && !stackOne.getTagCompound().getBoolean("complete") && this.getEnergyStored() >= 1) {
				
				if (stackOne.getTagCompound().getInteger("extractorDuration") < TimeHelper.mcToTick(100, 0, 0)) {
					stackOne.getTagCompound().setInteger("extractorDuration", stackOne.getTagCompound().getInteger("extractorDuration") + 2);
				}
				
				if (stackOne.getTagCompound().getInteger("extractorDuration") > TimeHelper.mcToTick(100, 0, 0)) {
					stackOne.getTagCompound().setInteger("extractorDuration", (int) TimeHelper.mcToTick(100, 0, 0));
				}

				this.setEnergyStored(this.getEnergyStored() - 1);
			}
		}
	}
}
