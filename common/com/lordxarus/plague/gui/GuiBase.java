package com.lordxarus.plague.gui;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.lordxarus.plague.Plague;

public class GuiBase extends GuiContainer {

	private Block block;
	private ResourceLocation texture;

	public GuiBase(InventoryPlayer invPlayer, IInventory entity, Block block, Container container) {
		super(container);

		this.block = block;
		texture = new ResourceLocation(Plague.modid, "textures/gui/" + block.getUnlocalizedName().substring(5) + ".png");

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
		String s = block.getLocalizedName();
		fontRenderer.drawString(s, 8, 6, 4210752);
		fontRenderer.drawString(I18n.getString("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

}