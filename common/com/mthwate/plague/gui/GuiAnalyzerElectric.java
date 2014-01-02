package com.mthwate.plague.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import com.mthwate.plague.block.BlockPlague;
import com.mthwate.plague.container.ContainerAnalyzerElectric;
import com.mthwate.plague.tileentity.TileEntityAnalyzerElectric;

public class GuiAnalyzerElectric extends GuiBase {

	TileEntityAnalyzerElectric tileEntity;

	public GuiAnalyzerElectric(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity, BlockPlague.analyzerElectric, new ContainerAnalyzerElectric(invPlayer, entity));
		tileEntity = (TileEntityAnalyzerElectric) entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int j, int i) {
		super.drawGuiContainerForegroundLayer(j, i);
		Integer s = (int) tileEntity.getEnergyStored();
		fontRenderer.drawString(s.toString(), 8, 26, 4210752);
	}

}
