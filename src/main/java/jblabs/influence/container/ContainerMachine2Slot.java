package jblabs.influence.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jblabs.influence.recipe.MachineRecipeHandler;
import jblabs.influence.tile.TileMachine2Slot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerMachine2Slot extends Container {
	
	private TileMachine2Slot tile;
	private int lastprogress;
	private int lastenergy;
	private MachineRecipeHandler recipes;
	
	public ContainerMachine2Slot(InventoryPlayer playerinv, TileMachine2Slot machinetile, MachineRecipeHandler recipes)
    {
        this.tile = machinetile;
        this.recipes = recipes;
        this.addSlotToContainer(new Slot(machinetile, 0, 56, 35));
        this.addSlotToContainer(new Slot(machinetile, 1, 116, 35));
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerinv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(playerinv, i, 8 + i * 18, 142));
        }
    }
	
	public void addCraftingToCrafters(ICrafting craft){
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, 0, this.tile.progressTime);
		craft.sendProgressBarUpdate(this, 1, this.tile.getEnergyStored(null));
	}
	
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		for(int i = 0; i < this.crafters.size(); ++i){
			ICrafting craft = (ICrafting) this.crafters.get(i);
			
			if(this.lastprogress != this.tile.progressTime){
				craft.sendProgressBarUpdate(this, 0, this.tile.progressTime);
			}
			if(this.lastenergy != this.tile.getEnergyStored(null)) {
				craft.sendProgressBarUpdate(this, 1, this.tile.getEnergyStored(null));
			}
			
		}
		
		this.lastprogress = this.tile.progressTime;
		this.lastenergy = this.tile.getEnergyStored(null);
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value){
		if(id == 0){
			this.tile.progressTime = value;
		} else if (id == 1) {
			this.tile.setEnergyStored(value);
		}
		
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tile.isUseableByPlayer(player);
	}
	
    public ItemStack transferStackInSlot(EntityPlayer player, int slotnum)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotnum);
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (slotnum == 1) {
            	if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (slotnum != 1 && slotnum != 0){
            	if (recipes.getRecipeResult(itemstack1) != null) {
            		if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return null;
                    }
            	}
            }
        }
        return itemstack;
    }
	
}
