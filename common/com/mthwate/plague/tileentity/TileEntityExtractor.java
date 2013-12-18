package com.mthwate.plague.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.mthwate.bookcase.TimeHelper;
import com.mthwate.plague.Plague;
import com.mthwate.plague.block.BlockPlague;
import com.mthwate.plague.disease.Disease;
import com.mthwate.plague.item.ItemPlague;

public class TileEntityExtractor extends TileEntityBase {

	public TileEntityExtractor() {
		super(BlockPlague.extractor, 2);
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
				syringeStack.getTagCompound().setString(Plague.modid + ".passive.owner", stackZero.getTagCompound().getString("owner"));

				ItemStack itemStack = new ItemStack(ItemPlague.diseaseVileFull);
				itemStack.setTagCompound(new NBTTagCompound());
				itemStack.getTagCompound().setString("owner", stackZero.getTagCompound().getString("owner"));
				itemStack.getTagCompound().setBoolean("complete", false);

				for (Disease disease : Plague.diseases) {
					if (stackZero.getTagCompound().getBoolean(disease.getUnlocalizedName())) {
						itemStack.getTagCompound().setBoolean(disease.getUnlocalizedName(), true);
						syringeStack.getTagCompound().setBoolean(Plague.modid + ".passive." + disease.getUnlocalizedName(), true);
					}
				}
				
				setInventorySlotContents(0, new ItemStack(ItemPlague.syringeEmpty));
				setInventorySlotContents(1, itemStack);
			}
		}

		if (stackOne != null) {
			if (stackOne.getItem().itemID == ItemPlague.diseaseVileFull.itemID && !stackOne.getTagCompound().getBoolean("complete")) {
				if (stackOne.getTagCompound().getInteger("extractorDuration") < TimeHelper.mcToTick(100, 0, 0)) {
					stackOne.getTagCompound().setInteger("extractorDuration", stackOne.getTagCompound().getInteger("extractorDuration") + 1);
				}
			}
		}
	}

}
