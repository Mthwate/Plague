package com.mthwate.plague.tileentity;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.mthwate.bookcase.TimeHelper;
import com.mthwate.plague.Plague;
import com.mthwate.plague.disease.Disease;
import com.mthwate.plague.item.ItemPlague;
import com.mthwate.plague.lib.InstrumentHelper;

public class TileEntityBoiler extends TileEntityBase {

	private int remainingBurnTime = 0;
	private int remainingCleaner = 0;
	
	private Map<Integer, Integer> fuelItems = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> cleanerItems = new HashMap<Integer, Integer>();
	
	public TileEntityBoiler() {
		super(3);
		
		fuelItems.put(Item.coal.itemID, 14);
		
		cleanerItems.put(Item.bucketWater.itemID, 14);
	}

	@Override
	public void updateEntity() {
		ItemStack slotItem = getStackInSlot(0);
		ItemStack slotCleaner = getStackInSlot(1);
		ItemStack slotFuel = getStackInSlot(2);
		
		if (slotFuel != null && slotCleaner != null && slotItem != null) {
			if (fuelItems.containsKey(slotFuel.itemID) && cleanerItems.containsKey(slotCleaner.itemID) && slotItem.itemID == ItemPlague.syringeEmpty.itemID && !isBurning()) {
				if(slotFuel.stackSize > 1) {
					slotFuel.stackSize--;
				} else {
					setInventorySlotContents(2, null);
				}
				remainingBurnTime = (int) TimeHelper.timeToTick(fuelItems.get(slotFuel.itemID));
			}
		}
		
		if (slotCleaner != null && isBurning()) {
			if (cleanerItems.containsKey(slotCleaner.itemID) && remainingCleaner <= 0) {
				setInventorySlotContents(1, new ItemStack(Item.bucketEmpty));
				remainingCleaner = (int) TimeHelper.timeToTick(cleanerItems.get(slotCleaner.itemID));
			}
		}
		
		if (slotItem != null && isCleaning()) {
			if (slotItem.itemID == ItemPlague.syringeEmpty.itemID) {
				for (Disease disease : InstrumentHelper.getRemnants(slotItem)) {
					if (Plague.rand.nextInt(100) < 1) {
						InstrumentHelper.removeRemnants(slotItem, disease);
					}
				}
			}
		}
		
		remainingBurnTime--;
		remainingCleaner--;
	}
	
	public int getRemainingBurnTime() {
		return remainingBurnTime;
	}
	
	public boolean isBurning() {
		return(getRemainingBurnTime() > 0);
	}
	
	public int getRemainingCleaner() {
		return remainingCleaner;
	}
	
	public boolean isCleaning() {
		return(getRemainingCleaner() > 0);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		if (slot == 0 && itemStack.itemID == ItemPlague.syringeEmpty.itemID) {
			return true;
		}
		if (slot == 1 && this.cleanerItems.containsKey(itemStack.itemID)) {
			return true;
		}
		if (slot == 2 && this.fuelItems.containsKey(itemStack.itemID)) {
			return true;
		}
		return false;
	}

}
