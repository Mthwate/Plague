package com.mthwate.plague.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.common.ForgeDirection;

import com.mthwate.plague.Plague;

public class GuiExtractorElectric extends GuiElectric {

	public GuiExtractorElectric(InventoryPlayer invPlayer, IInventory entity, Container container) {
		super(invPlayer, entity, container);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int j, int i) {
		super.drawGuiContainerForegroundLayer(j, i);

		this.updateButtonSide(5, 0);
		this.updateButtonSide(6, 1);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initGui() {
		super.initGui();
		
		buttonList.add(new GuiButton(5, (int) (this.guiLeft + ((this.xSize/100.0)*16)), (int) (this.guiTop + ((this.ySize/100.0)*34)), 38, 14, ForgeDirection.getOrientation(this.tileEntityElectric.getOutDirection(0)).name()));
		buttonList.add(new GuiButton(6, (int) (this.guiLeft + ((this.xSize/100.0)*40)), (int) (this.guiTop + ((this.ySize/100.0)*34)), 38, 14, ForgeDirection.getOrientation(this.tileEntityElectric.getOutDirection(1)).name()));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		super.actionPerformed(guibutton);
		
		switch (guibutton.id) {
			
			case 5:
				Plague.proxy.clickOutButton(this.tileEntityBase.worldObj.provider.dimensionId, this.tileEntityBase.xCoord, this.tileEntityBase.yCoord, this.tileEntityBase.zCoord, 0);
				break;
				
			case 6:
				Plague.proxy.clickOutButton(this.tileEntityBase.worldObj.provider.dimensionId, this.tileEntityBase.xCoord, this.tileEntityBase.yCoord, this.tileEntityBase.zCoord, 1);
				break;
		}
	}

}
