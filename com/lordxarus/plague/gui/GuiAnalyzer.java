package com.lordxarus.plague.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.lordxarus.plague.Plague;
import com.lordxarus.plague.block.BlockPlague;
import com.lordxarus.plague.container.ContainerAnalyzer;
import com.lordxarus.plague.tileentity.TileEntityAnalyzer;

public class GuiAnalyzer extends GuiContainer {

	public static final ResourceLocation texture = new ResourceLocation(Plague.modid, "textures/gui/" + BlockPlague.analyzer.getUnlocalizedName().substring(5) + ".png");
	
	public GuiAnalyzer(InventoryPlayer invPlayer, TileEntityAnalyzer entity) {
		super(new ContainerAnalyzer(invPlayer, entity));
		
		xSize = 176;
		ySize = 166;
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int j, int i) {
		String s = BlockPlague.analyzer.getLocalizedName();
		this.fontRenderer.drawString(s, 8, 6, 4210752);
		this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float f, int j, int i) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
}
