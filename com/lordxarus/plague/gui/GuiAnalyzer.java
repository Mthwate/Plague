package com.lordxarus.plague.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.lordxarus.plague.Plague;
import com.lordxarus.plague.container.ContainerAnalyzer;
import com.lordxarus.plague.tileentity.TileEntityAnalyzer;

public class GuiAnalyzer extends GuiContainer {

	public static final ResourceLocation texture = new ResourceLocation(Plague.modid, "textures/gui/" + Plague.blockAnalyzer.getUnlocalizedName().substring(5) + ".png");
	
	public GuiAnalyzer(InventoryPlayer invPlayer, TileEntityAnalyzer entity) {
		super(new ContainerAnalyzer(invPlayer, entity));
		
		xSize = 176;
		ySize = 166;
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float f, int j, int i) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
}
