package com.lordxarus.plague.item;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.mthwate.bookcase.TimeHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDiseaseVileFull extends ItemBase {
	
	public ItemDiseaseVileFull(int id) {
		super(id);
		this.maxStackSize = 1;
	}
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
		if (stack.getTagCompound() != null) {
			boolean complete = stack.getTagCompound().getBoolean("complete");
			if ((!complete) && (entity instanceof EntityPlayer)) {
				stack.getTagCompound().setBoolean("complete", true);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List dataList, boolean bool) {
		if (itemStack.getTagCompound() != null) {
			double extractorDuration = TimeHelper.tickToMc(itemStack.getTagCompound().getInteger("extractorDuration"));
			double analyzerDuration = TimeHelper.tickToMc(itemStack.getTagCompound().getInteger("analyzerDuration"));
			
			extractorDuration = extractorDuration / (60 * 60);
			analyzerDuration = analyzerDuration / (60 * 60);
			
			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.FLOOR);
			
			String roundedExtractorDuration = df.format(extractorDuration);
			String roundedAnalyzerDuration = df.format(analyzerDuration);
			
			dataList.add(roundedExtractorDuration + "% Extracted");
			dataList.add(roundedAnalyzerDuration + "% Analyzed");
		}
	}

}
