package com.mthwate.plague.gui;

import universalelectricity.api.energy.UnitDisplay;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import com.mthwate.plague.block.BlockPlague;
import com.mthwate.plague.container.ContainerExtractorElectric;
import com.mthwate.plague.tileentity.TileEntityExtractorElectric;

public class GuiExtractorElectric extends GuiBase {

	TileEntityExtractorElectric tileEntity;

	public GuiExtractorElectric(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity, BlockPlague.extractorElectric, new ContainerExtractorElectric(invPlayer, entity));
		tileEntity = (TileEntityExtractorElectric) entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int j, int i) {
		super.drawGuiContainerForegroundLayer(j, i);
		fontRenderer.drawString(UnitDisplay.getDisplay(tileEntity.getEnergy(null), UnitDisplay.Unit.JOULES), 8, 26, 4210752);
	}

}
