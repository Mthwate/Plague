package com.mthwate.plague.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.UniversalClass;
import universalelectricity.api.energy.EnergyStorageHandler;
import universalelectricity.api.energy.IEnergyContainer;
import universalelectricity.api.energy.IEnergyInterface;

import com.mthwate.plague.Plague;
import com.mthwate.plague.block.BlockContainerElectricBase;

@UniversalClass
public abstract class TileEntityBaseElectric extends TileEntityBase implements IEnergyContainer, IEnergyInterface {
	
	private EnergyStorageHandler energyStorage;

	public TileEntityBaseElectric(int slots) {
		super(slots);
		this.energyStorage = new EnergyStorageHandler((long) (125000000 * Math.pow(4, 10)));
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		long cap = (long) (125000000 * Math.pow(4, this.getTier()));
		
		if (this.energyStorage.getEnergyCapacity() != cap) {
			this.energyStorage.setCapacity(cap);
		}
		
		Plague.proxy.updateElectricity(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.getEnergy(null));
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
	
}
