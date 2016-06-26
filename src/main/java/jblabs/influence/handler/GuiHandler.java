package jblabs.influence.handler;

import java.util.ArrayList;
import java.util.HashMap;

import cpw.mods.fml.common.network.IGuiHandler;
import jblabs.influence.client.gui.GuiMachine2Slot;
import jblabs.influence.container.ContainerMachine2Slot;
import jblabs.influence.recipe.MachineRecipeHandler;
import jblabs.influence.tile.TileMachine2Slot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
	
	private static GuiHandler handler = new GuiHandler();
	
	public static GuiHandler handler() {
		return handler;
	}
	
	private ArrayList<GuiDescriptor> guiList = new ArrayList<GuiDescriptor>();
	private HashMap<String, Integer> IDmap = new HashMap<String, Integer>();
	
	public int addGui(GuiDescriptor guiInfo) {
		int x = this.getNextID();
		guiInfo.id = x;
		IDmap.put(guiInfo.name, x);
		guiList.add(guiInfo);
		return x;
		
	}
	
	public int getID(String name) {
		return IDmap.get(name);
	}
	
	private int getNextID() {
		return guiList.size();
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity != null) {
			GuiDescriptor guiInfo = guiList.get(ID);
			switch (guiInfo.slots) {
			case 2:
				return new ContainerMachine2Slot(player.inventory, (TileMachine2Slot) tileEntity, guiInfo.recipes);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity != null) {
			GuiDescriptor guiInfo = guiList.get(ID);
			switch (guiInfo.slots) {
			case 2:
				return new GuiMachine2Slot(player.inventory, (TileMachine2Slot) tileEntity, guiInfo.recipes, guiInfo.texturepath);
			}
		}
		return null;
	}

}
