package com.mthwate.plague.tileentity;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.UniversalClass;
import universalelectricity.api.energy.EnergyStorageHandler;
import universalelectricity.api.energy.IEnergyContainer;
import universalelectricity.api.energy.IEnergyInterface;

import com.mthwate.plague.Plague;
import com.mthwate.plague.block.BlockContainerElectricBase;

@UniversalClass
public abstract class TileEntityBaseElectric extends TileEntityBase implements IEnergyContainer, IEnergyInterface, ISidedInventory {
	
	private int[] outSides;
	
	private EnergyStorageHandler energyStorage;
	private long cap = -1;
	private int count = -1;
	private int outPercdent = 0;

	public TileEntityBaseElectric(int slots) {
		super(slots);
		outSides = new int[slots];
		this.energyStorage = new EnergyStorageHandler((long) (125000000 * Math.pow(4, 10)));
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(cap == -1) {
			cap = (long) (125000000 * Math.pow(4, this.getTier()));
		}
		
		if (this.energyStorage.getEnergyCapacity() != cap) {
			this.energyStorage.setCapacity(cap);
		}
		
		if (count == 10) {
			Plague.proxy.updateElectricity(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.getEnergy(null));
			Plague.proxy.updateOutPercent(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.getOutPercent());
			for (int i=0; i<outSides.length; i++) {
				Plague.proxy.updateOutSlot(this.worldObj, this.xCoord, this.yCoord, this.zCoord, i, outSides[i]);
			}
			count = -1;
		}
		count++;
	}

	@Override
	public boolean canConnect(ForgeDirection direction) {
		if (direction == null || direction.equals(ForgeDirection.UNKNOWN)) {
			return false;
		}
		return true;
	}

	@Override
	public long onExtractEnergy(ForgeDirection direction, long energy, boolean doExtract) {
		return 0;
	}

	@Override
	public long onReceiveEnergy(ForgeDirection direction, long energy, boolean doReceive) {
		return this.energyStorage.receiveEnergy(energy, doReceive);
	}

	@Override
	public long getEnergy(ForgeDirection direction) {
		return this.energyStorage.getEnergy();
	}

	@Override
	public long getEnergyCapacity(ForgeDirection direction) {
		return this.energyStorage.getEnergyCapacity();
	}

	@Override
	public void setEnergy(ForgeDirection direction, long energy) {
		this.energyStorage.setEnergy(energy);
	}
	
	public void modifyEnergyStored(long energy) {
		this.energyStorage.modifyEnergyStored(energy);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		this.energyStorage.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.energyStorage.readFromNBT(compound);
	}
	
	public int getTier() {
		return ((BlockContainerElectricBase) this.getBlockType()).getTier();
	}
	
	public long getEnergyUsage() {
		return (long) (2500 * Math.pow(4, this.getTier()));
	}

	@Override
	public String getInvName() {
		return this.getBlockType().getUnlocalizedName().substring(5, this.getBlockType().getUnlocalizedName().length()-2);
	}

	public void increaseOutPercent(int i) {
		this.outPercdent += i;
		if (this.outPercdent > 100) {
			this.outPercdent = 100;
		} else if (this.outPercdent < 0) {
			this.outPercdent = 0;
		}
	}

	public void setOutPercent(int i) {
		this.outPercdent = i;
	}

	public int getOutPercent() {
		return this.outPercdent;
	}
	
	public void changeOutDirection(int slot) {
		outSides[slot]++;
		if (outSides[slot] > ForgeDirection.VALID_DIRECTIONS.length) {
			outSides[slot] = 0;
		}
	}
	
	public int getOutDirection(int slot) {
		return outSides[slot];
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
		return this.isItemValidForSlot(slot, itemStack);
	}

	public void setOutSlot(int slot, int side) {
		outSides[slot] = side;
	}
	
}
