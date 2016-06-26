
package jblabs.influence.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jblabs.influence.InfluenceMod;
import jblabs.influence.container.ContainerMachine2Slot;
import jblabs.influence.recipe.MachineRecipeHandler;
import jblabs.influence.tile.TileMachine2Slot;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GuiMachine2Slot extends GuiContainer
{
	
	private static ResourceLocation guiTextures;

    private final InventoryPlayer inventoryPlayer;

    private final TileMachine2Slot tile;

    public GuiMachine2Slot(InventoryPlayer playerinv, 
          TileMachine2Slot machinetile,MachineRecipeHandler recipes, String texturePath)
    {
        super(new ContainerMachine2Slot(playerinv, 
              machinetile, recipes));
        inventoryPlayer = playerinv;
        tile = machinetile;
        guiTextures = new ResourceLocation(texturePath);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
    	String s = this.tile.hasCustomInventoryName() ? this.tile.getInventoryName() : I18n.format("tile." + this.tile.getInventoryName() + ".name", new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, 
          int mouseX, int mouseY)
    {
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(guiTextures);
        int marginHorizontal = (width - xSize) / 2;
        int marginVertical = (height - ySize) / 2;
        drawTexturedModalRect(marginHorizontal, marginVertical, 0, 0, 
              xSize, ySize);

        int progressLevel = getProgressLevel(24);
        drawTexturedModalRect(marginHorizontal + 79, marginVertical + 34, 
              176, 14, progressLevel + 1, 16);
        inventoryPlayer.player.addChatMessage(new ChatComponentText("Progress: " + progressLevel));
        int energyLevel = getEnergyLevel(40);
        drawTexturedModalRect(marginHorizontal + 147, marginVertical + 23, 
                176, 31, 7, energyLevel+1);
    }
 
    private int getProgressLevel(int progressIndicatorPixelWidth)
    {
        return (int) (((float) tile.progressTime/tile.processingTime) * progressIndicatorPixelWidth);
    }
    
    private int getEnergyLevel(int pixelWidth) {
    	return (int) ((tile.getEnergyStored(null)/tile.getMaxEnergyStored(null)) * pixelWidth);
    }
 }
