package com.mthwate.plague.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import universalelectricity.api.energy.UnitDisplay;

import com.mthwate.plague.tileentity.TileEntityBaseElectric;

public class GuiElectric extends GuiBase {

	TileEntityBaseElectric tileEntityElectric;

	public GuiElectric(InventoryPlayer invPlayer, IInventory entity, Container container) {
		super(invPlayer, entity, container);
		tileEntityElectric = (TileEntityBaseElectric) entity;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int j, int i) {
		super.drawGuiContainerForegroundLayer(j, i);
		fontRenderer.drawString(UnitDisplay.getDisplay(tileEntityElectric.getEnergy(null), UnitDisplay.Unit.JOULES), 8, 26, 4210752);
	}

}
