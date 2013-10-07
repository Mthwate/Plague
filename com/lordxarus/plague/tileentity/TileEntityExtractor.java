package com.lordxarus.plague.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.lordxarus.plague.Plague;
import com.lordxarus.plague.disease.Disease;
import com.lordxarus.plague.lib.TimeHelper;

public class TileEntityExtractor extends TileEntity implements IInventory {
	
	private ItemStack[] inventory;

	public TileEntityExtractor() {
		//sets the number of slots in the inventory
		inventory = new ItemStack[2];
	}

	@Override
	public int getSizeInventory() {
		return(inventory.length);
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return(inventory[slot]);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack itemstack = getStackInSlot(slot);
		if(itemstack != null) {
			if(itemstack.stackSize <= amount) {
				setInventorySlotContents(slot, null);
			} else {
				itemstack = itemstack.splitStack(amount);
				onInventoryChanged();
			}
		}
		return(itemstack);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack itemstack = getStackInSlot(slot);
		setInventorySlotContents(slot, null);
		return(itemstack);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		inventory[slot] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return(Plague.blockExtractor.getUnlocalizedName().substring(5));
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return(64);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return(player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64);
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		return(true);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); i++) {
			ItemStack itemstack = getStackInSlot(i);
			if(itemstack != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("SlotExtractor", (byte) i);
				itemstack.writeToNBT(item);
				list.appendTag(item);
			}
		}
		compound.setTag("ItemsExtractor", list);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList list = compound.getTagList("ItemsExtractor");
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound) list.tagAt(i);
			int slot = item.getByte("SlotExtractor");
			if(slot >= 0 && slot < getSizeInventory()) {
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
	}
	
	@Override
	public void updateEntity() {
		ItemStack stackZero = this.getStackInSlot(0);
		ItemStack stackOne = this.getStackInSlot(1);
		if ((stackZero != null) && (stackOne != null)) {
			if ((stackZero.getItem().itemID == Plague.itemSyringeFull.itemID) && (stackOne.getItem().itemID == Plague.itemDiseaseVileEmpty.itemID)) {
				
				ItemStack itemStack = new ItemStack(Plague.itemDiseaseVileFull);
				itemStack.setTagCompound(new NBTTagCompound());
				itemStack.getTagCompound().setString("owner", stackZero.getTagCompound().getString("owner"));
				itemStack.getTagCompound().setBoolean("complete", false);
				
				for (Disease disease : Plague.diseases) {
					if (stackZero.getTagCompound().getBoolean(disease.getUnlocalizedName())) {
						itemStack.getTagCompound().setBoolean(disease.getUnlocalizedName(), true);
					}
				}
				
				setInventorySlotContents(1, itemStack);
				decrStackSize(0,1);
			}
		}
		
		if (stackOne != null) {
			if ((stackOne.getItem().itemID == Plague.itemDiseaseVileFull.itemID) && (!stackOne.getTagCompound().getBoolean("complete"))) {
				if (stackOne.getTagCompound().getInteger("extractorDuration") < TimeHelper.mcToTick(100, 0, 0)) {
					stackOne.getTagCompound().setInteger("extractorDuration", stackOne.getTagCompound().getInteger("extractorDuration") + 1);
				}
			}
		}
	}

}
