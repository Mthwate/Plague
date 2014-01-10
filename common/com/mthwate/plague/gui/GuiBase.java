package com.mthwate.plague.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.mthwate.plague.Plague;
import com.mthwate.plague.tileentity.TileEntityBase;

public class GuiBase extends GuiContainer {
	
	private ResourceLocation texture;
	TileEntityBase tileEntityBase;

	public GuiBase(InventoryPlayer invPlayer, IInventory entity, Container container) {
		super(container);
		
		tileEntityBase = (TileEntityBase) entity;

		texture = new ResourceLocation(Plague.modid, "textures/gui/" + tileEntityBase.getInvName() + ".png");

		xSize = 176;
		ySize = 166;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int j, int i) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int j, int i) {
		String s = tileEntityBase.getBlockType().getLocalizedName();
		fontRenderer.drawString(s, 8, 6, 4210752);
		fontRenderer.drawString(I18n.getString("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

}