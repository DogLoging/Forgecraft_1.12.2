package com.modding.forgecraft.inventory;

import com.modding.forgecraft.Main;
import com.modding.forgecraft.block.container.ContainerFusionFurnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiFusionFurnace extends GuiContainer
{
    private static final ResourceLocation fusionGuiTextures = new ResourceLocation(Main.MODID + ":textures/gui/container/fusion_furnace_gui.png");
    private final InventoryPlayer player;
    private final IInventory tileFusion;

    public GuiFusionFurnace(InventoryPlayer parInventoryPlayer, IInventory parInventoryGrinder)
    {
        super(new ContainerFusionFurnace(parInventoryPlayer,  parInventoryGrinder));
        
        player = parInventoryPlayer;
        tileFusion = parInventoryGrinder;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = tileFusion.getDisplayName().getUnformattedText();
        
        fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        fontRenderer.drawString(player.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(fusionGuiTextures);
        int marginHorizontal = (width - xSize) / 2;
        int marginVertical = (height - ySize) / 2;
        drawTexturedModalRect(marginHorizontal, marginVertical, 0, 0, xSize, ySize);
        
        int fuelLevel = getFuelLevel(24);
        drawTexturedModalRect(marginHorizontal + 48, marginVertical + 55, 4, 167, fuelLevel, 14);
        
        int timeFusionLevel = getTimeFusionLevel(13);
        drawTexturedModalRect(marginHorizontal + 48, marginVertical + 23 + 14 - timeFusionLevel, 176, 14 - timeFusionLevel, 14, timeFusionLevel + 1);
        
        
        int timeprocessLevel = getProcessTimeLevel(24);
        drawTexturedModalRect(marginHorizontal + 91, marginVertical + 22, 176, 14, timeprocessLevel + 1, 16);
    }
    
    private int getProcessTimeLevel(int pixel)
    {
        int i = this.tileFusion.getField(3);
        int j = this.tileFusion.getField(1);
        return j != 0 && i != 0 ? i * pixel / j : 0;
    }
    
    private int getTimeFusionLevel(int pixel)
    {
        int i = this.tileFusion.getField(1);

        if (i == 0)
        {
            i = 200;
        }
        
    	return this.tileFusion.getField(2) * i / 16;
    	
    }

	private int getFuelLevel(int pixel)
	{
		  int currentFuel = tileFusion.getField(5); // campo que contém a quantidade de combustível
		  int maxFuel = 100; // valor máximo de combustível suportado
		  return currentFuel * pixel / maxFuel;
	}
}
