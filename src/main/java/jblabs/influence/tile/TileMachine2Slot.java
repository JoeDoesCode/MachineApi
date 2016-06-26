package jblabs.influence.tile;

import jblabs.influence.block.BlockStatedMachine2Slot;
import jblabs.influence.recipe.MachineRecipeHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class TileMachine2Slot extends TileMachine {

	public TileMachine2Slot() {
		super();
		this.inventory = new ItemStack[2];
		
	}

	@Override
	public void updateOwnBlockState() {
		BlockStatedMachine2Slot.updateBlockState(this.isWorking(), this.machineName, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		
	}

	@Override
	public boolean canProcess() {
		if (this.hasEnergyLeft()) {
			if (this.inventory[0] == null)
	        {
	            return false;
	        }
	        else
	        {
	            ItemStack itemstack = this.recipes.getRecipeResult(this.inventory[0]);
	            if (itemstack == null) return false;
	            if (this.inventory[1] == null) return true;
	            if (!this.inventory[1].isItemEqual(itemstack)) return false;
	            int result = inventory[1].stackSize + itemstack.stackSize;
	            return result <= getInventoryStackLimit() && result <= this.inventory[1].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
	        }
		} else {
			return false;
		}
	}

	@Override
	public void processItem() {
		if (this.canProcess()) {
			ItemStack result = recipes.getRecipeResult(this.inventory[0]);
			
			if (this.inventory[1] == null)
            {
                this.inventory[1] = result.copy();
            }
            else if (this.inventory[1].getItem() == result.getItem())
            {
                this.inventory[1].stackSize += result.stackSize;
            }
			
			--this.inventory[0].stackSize;

            if (this.inventory[0].stackSize <= 0)
            {
                this.inventory[0] = null;
            }
		}
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		return slot == 1 ? false : true;
	}

}
