package com.mthwate.plague.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import com.mthwate.plague.container.ContainerBoiler;
import com.mthwate.plague.tileentity.TileEntityBoiler;

public class GuiBoiler extends GuiBase {

	private TileEntityBoiler inv;

	public GuiBoiler(InventoryPlayer invPlayer, IInventory entity) {
		super(invPlayer, entity, new ContainerBoiler(invPlayer, entity));
		inv = (TileEntityBoiler) entity;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int j, int i) {
		super.drawGuiContainerBackgroundLayer(f, j, i);

        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
		if (inv.isBurning()) {
			int rbt = inv.getRemainingBurnTime()/20 + 1;
            this.drawTexturedModalRect(k + 62, l + 66 - rbt, 176, 14 - rbt, 14, rbt);
		}
		
		if (inv.isCleaning()) {
			int rct = inv.getRemainingCleaner()/20 + 1;
            this.drawTexturedModalRect(k + 80, l + 49 - rct, 176, 28 - rct, 14, rct);
		}
	}
}
