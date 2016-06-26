package jblabs.influence.recipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class MachineRecipeHandler {
	
	private Map recipeList = new HashMap();
	
	public void registerBlockRecipe(Block input, ItemStack output)
    {
        this.registerItemRecipe(Item.getItemFromBlock(input), output);
    }

    public void registerItemRecipe(Item input, ItemStack output)
    {
        this.registerRecipe(new ItemStack(input, 1, 32767), output);
    }

    public void registerRecipe(ItemStack input, ItemStack output)
    {
        this.recipeList.put(input, output);
    }
    
    public ItemStack getRecipeResult(ItemStack input)
    {
        Iterator iterator = this.recipeList.entrySet().iterator();
        Entry entry;

        do
        {
            if (!iterator.hasNext())
            {
                return null;
            }

            entry = (Entry)iterator.next();
        }
        while (!this.is_identical(input, (ItemStack)entry.getKey()));

        return (ItemStack)entry.getValue();
    }

    private boolean is_identical(ItemStack p_151397_1_, ItemStack p_151397_2_)
    {
        return p_151397_2_.getItem() == p_151397_1_.getItem() && (p_151397_2_.getItemDamage() == 32767 || p_151397_2_.getItemDamage() == p_151397_1_.getItemDamage());
    }
    
    public Map getRecipeList()
    {
        return this.recipeList;
    }
    

}
