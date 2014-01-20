package com.mthwate.plague.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.energy.UnitDisplay;

import com.mthwate.plague.Plague;
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
		fontRenderer.drawString(UnitDisplay.getDisplay(tileEntityElectric.getEnergy(null), UnitDisplay.Unit.JOULES), 16, 20, 4210752);
		fontRenderer.drawString(String.valueOf(tileEntityElectric.getOutPercent()), 140, 44, 4210752);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initGui() {
		super.initGui();

		buttonList.add(new GuiButton(1, (int) (this.guiLeft + ((this.xSize/100.0)*69)), (int) (this.guiTop + ((this.ySize/100.0)*16)), 22, 14, "+1"));
		buttonList.add(new GuiButton(2, (int) (this.guiLeft + ((this.xSize/100.0)*69)), (int) (this.guiTop + ((this.ySize/100.0)*34)), 22, 14, "-1"));
		buttonList.add(new GuiButton(3, (int) (this.guiLeft + ((this.xSize/100.0)*83)), (int) (this.guiTop + ((this.ySize/100.0)*16)), 22, 14, "+10"));
		buttonList.add(new GuiButton(4, (int) (this.guiLeft + ((this.xSize/100.0)*83)), (int) (this.guiTop + ((this.ySize/100.0)*34)), 22, 14, "-10"));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		switch(guibutton.id) {
			
			case 1:
				Plague.proxy.clickPercentButton(this.tileEntityBase.worldObj.provider.dimensionId, this.tileEntityBase.xCoord, this.tileEntityBase.yCoord, this.tileEntityBase.zCoord, 1);
				break;
				
			case 2:
				Plague.proxy.clickPercentButton(this.tileEntityBase.worldObj.provider.dimensionId, this.tileEntityBase.xCoord, this.tileEntityBase.yCoord, this.tileEntityBase.zCoord, -1);
				break;
				
			case 3:
				Plague.proxy.clickPercentButton(this.tileEntityBase.worldObj.provider.dimensionId, this.tileEntityBase.xCoord, this.tileEntityBase.yCoord, this.tileEntityBase.zCoord, 10);
				break;
				
			case 4:
				Plague.proxy.clickPercentButton(this.tileEntityBase.worldObj.provider.dimensionId, this.tileEntityBase.xCoord, this.tileEntityBase.yCoord, this.tileEntityBase.zCoord, -10);
				break;
		}
	}
	
	protected void updateButtonSide(int button, int slot) {
		ForgeDirection directionZero = ForgeDirection.getOrientation(this.tileEntityElectric.getOutDirection(slot));
		if (directionZero == ForgeDirection.UNKNOWN) {
			((GuiButton) this.buttonList.get(button - 1)).displayString = "NONE";
		} else {
			((GuiButton) this.buttonList.get(button - 1)).displayString = directionZero.name();
		}
	}

}