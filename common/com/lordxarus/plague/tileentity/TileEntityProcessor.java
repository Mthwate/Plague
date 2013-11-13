package com.lordxarus.plague.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.lordxarus.plague.Plague;
import com.lordxarus.plague.block.BlockPlague;
import com.lordxarus.plague.disease.Disease;
import com.lordxarus.plague.item.ItemPlague;
import com.mthwate.bookcase.Rand;
import com.mthwate.bookcase.TimeHelper;

public class TileEntityProcessor extends TileEntityBase {

	public TileEntityProcessor() {
		super(BlockPlague.processor, 2);
	}
	
	@Override
	public void updateEntity() {
		
		//gets the item stacks in all inventory slots
		ItemStack stackZero = this.getStackInSlot(0);
		ItemStack stackOne = this.getStackInSlot(1);
		
		if ((stackZero != null) && (stackOne != null)) {
			
			if ((stackZero.getItem().itemID == ItemPlague.diseaseVileFull.itemID) && (stackOne.getItem().itemID == ItemPlague.syringeEmpty.itemID)) {
				
				//a list of all diseases the disease vile contains
				List<String> diseaseNames = new ArrayList<String>();
				
				//checks what diseases the disease filled vile contains
				for (Disease disease : Plague.diseases) {
					if (stackZero.getTagCompound().getBoolean(disease.getUnlocalizedName())) {
						diseaseNames.add(disease.getUnlocalizedName());
					}
				}
				
				//if the disease filled vile contains at least 1 disease
				if (diseaseNames.size() > 0) {
					
					//make a new cure item
					ItemStack itemStack = new ItemStack(ItemPlague.cure);
					
					//initialize the item's NBT tag compound
					itemStack.setTagCompound(new NBTTagCompound());
					
					//set the owner of the disease to the cure item
					itemStack.getTagCompound().setString("owner", stackZero.getTagCompound().getString("owner"));
					
					//picks a Random disease in the disease filled vile
					String cureDisease = diseaseNames.get(Rand.nextInt(diseaseNames.size()));
					
					//sets the disease to the item cure
					itemStack.getTagCompound().setString("cureDisease", cureDisease);
					
					//gets the time the vile was in the analyzer and extractor
					double analyzerDuration = TimeHelper.tickToMc(stackZero.getTagCompound().getInteger("analyzerDuration")) / (60 * 60);
					double extractorDuration = TimeHelper.tickToMc(stackZero.getTagCompound().getInteger("extractorDuration")) / (60 * 60);
					
					//sets the chance that the cure will work based on the time in the analyzer and extractor
					int chance = (int) ((analyzerDuration * extractorDuration));
					
					String displayDisease = null;
					
					//if the cure will work
					if (Rand.nextInt(10000) < chance) {
						
						//set the display name to the disease it is curing
						displayDisease = cureDisease;
						
					//if the cure will not work
					} else {
						
						//sets the display name to a Random disease other than the correct one
						displayDisease = cureDisease;
						while (displayDisease.equals(cureDisease)) {
							displayDisease = Plague.diseases.get(Rand.nextInt(Plague.diseases.size())).getUnlocalizedName();
						}
						
					}
					
					//sets the display name of the cure
					itemStack.getTagCompound().setString("displayDisease", displayDisease);
					
					//empties the disease vile and gives the player a cure
					setInventorySlotContents(1, itemStack);
					setInventorySlotContents(0, new ItemStack(ItemPlague.diseaseVileEmpty));
				
				//if the disease filled vile does not contain a disease
				} else {
					
					//empties the disease filled vile
					ItemStack itemStack = new ItemStack(ItemPlague.diseaseVileEmpty);
					setInventorySlotContents(0, itemStack);
					
				}
			}
		}
	}

}
