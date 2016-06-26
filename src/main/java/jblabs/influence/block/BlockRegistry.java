package jblabs.influence.block;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;
import jblabs.influence.recipe.MachineRecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BlockRegistry {
	private Map<String, Block> map = new HashMap<String, Block>();
	public static final BlockRegistry handlingBase = new BlockRegistry();
	
	public static BlockRegistry registry()
    {
        return handlingBase;
    }
	
	public Block getBlock(String name) {
		return map.get(name);
	}
	public Block getActiveBlock(String name) {
		return map.get(name + "Active");
	}
	public Item findItem(String name) {
		return Item.getItemFromBlock(map.get(name));
	}
	
	public void registerBlock(String name, Block block) {
		map.put(name, block);
		GameRegistry.registerBlock(block, name);
	}
}
