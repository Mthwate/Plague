package com.mthwate.plague.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.UniversalClass;
import universalelectricity.api.energy.EnergyStorageHandler;
import universalelectricity.api.energy.IEnergyContainer;
import universalelectricity.api.energy.IEnergyInterface;

import com.mthwate.plague.Plague;

@UniversalClass
public abstract class TileEntityBaseElectric extends TileEntityBase implements IEnergyContainer, IEnergyInterface {
	
	private EnergyStorageHandler energyStorage;

	public TileEntityBaseElectric(Block block, int slots) {
		super(block, slots);
		energyStorage = new EnergyStorageHandler(50000, 200);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
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
		return this.energyStorage.extractEnergy(energy, doExtract);
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
	
}
