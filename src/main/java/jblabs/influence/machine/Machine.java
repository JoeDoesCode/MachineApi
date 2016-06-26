package jblabs.influence.machine;

import jblabs.influence.InfluenceMod;
import jblabs.influence.block.BlockRegistry;
import jblabs.influence.block.BlockStatedMachine2Slot;
import jblabs.influence.handler.GuiDescriptor;
import jblabs.influence.handler.GuiHandler;
import jblabs.influence.recipe.MachineRecipeHandler;
import net.minecraft.creativetab.CreativeTabs;

public class Machine {
	public MachineRecipeHandler recipes;
	public String name;
	public int slots;
	public String blockTextureBase;
	
	public void preInit() {
		switch(slots) {
		case 2:
			BlockRegistry.registry().registerBlock(name, new BlockStatedMachine2Slot(false, name, blockTextureBase, recipes).setBlockName(name).setCreativeTab(CreativeTabs.tabBlock));
			BlockRegistry.registry().registerBlock(name+"Active", new BlockStatedMachine2Slot(true, name, blockTextureBase, recipes));
			
		}
	}
	public void registerGUI(GuiHandler gui) {
		GuiDescriptor guiInfo = new GuiDescriptor();
		guiInfo.recipes = recipes;
		guiInfo.slots = slots;
		guiInfo.texturepath = InfluenceMod.MODID+":textures/gui/container/2slotmachine.png";
		guiInfo.name = name;
		gui.addGui(guiInfo);
	}
}
