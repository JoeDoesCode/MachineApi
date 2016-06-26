package jblabs.influence.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Arrays;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import jblabs.influence.block.BlockRegistry;
import jblabs.influence.block.BlockStatedMachine;
import jblabs.influence.recipe.MachineRecipeHandler;



public abstract class TileMachine extends TileEntity implements IEnergyHandler, ISidedInventory {

	protected EnergyStorage storage;
	protected ItemStack[] inventory;
	
	protected static int[] slotsTop;
	protected static int[] slotsBottom;
	protected static int[] slotsSide;
	
	protected String machineName;
	
	public int progressTime;
	public int energyEffect = 200;
	public int processingTime = 200;
	
	public MachineRecipeHandler recipes;
	
	public TileMachine() {
		super();
		this.storage = new EnergyStorage(10000);
	}
	
	public TileMachine(String name, MachineRecipeHandler recipes) {
		this();
		this.recipes = recipes;
		this.machineName = name;
	}

	
	public void updateEntity() {
		boolean flag = this.isWorking();
		boolean flag1 = false;
		if (!this.worldObj.isRemote) {
			if (this.hasEnergyLeft() && this.canProcess()) {
				this.progressTime++;
				this.extractEnergy(null, energyEffect, false);
				if (this.progressTime == this.processingTime) {
					this.processItem();
					this.progressTime = 0;
					flag1 = true;
					
				}
			}
			else {
				this.progressTime = 0;
			}
		}
		if (flag != this.isWorking()) {
			flag1 = true;
			this.updateOwnBlockState();
		}
		if (flag1) {
			this.markDirty();
		}
	}
	
	public abstract void updateOwnBlockState();
		// B.updateBlockState(this.isWorking(), this.machineName, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	
	public boolean isWorking() {
		return (this.progressTime > 0 && this.hasEnergyLeft());
	}
	public abstract boolean canProcess();
	
	public abstract void processItem();
	
	
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		storage.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.inventory = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tabCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tabCompound1.getByte("Slot");

			if (byte0 >= 0 && byte0 < this.inventory.length) {
				this.inventory[byte0] = ItemStack.loadItemStackFromNBT(tabCompound1);
			}
		}
		
		this.machineName = tagCompound.getString("MachineName");
		BlockStatedMachine b = (BlockStatedMachine) BlockRegistry.registry().getBlock(this.machineName);
		this.recipes = b.recipes;
	}

	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		storage.writeToNBT(tagCompound);
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < this.inventory.length; ++i) {
			if (this.inventory[i] != null) {
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound1.setByte("Slot", (byte) i);
				this.inventory[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}
		
		tagCompound.setString("MachineName", machineName);

		tagCompound.setTag("Items", tagList);
	}
	

	/* IEnergyConnection */
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {

		return true;
	}

	/* IEnergyReceiver */
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {

		return storage.receiveEnergy(maxReceive, simulate);
	}

	/* IEnergyProvider */
	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {

		return storage.extractEnergy(maxExtract, simulate);
	}

	/* IEnergyReceiver and IEnergyProvider */
	@Override
	public int getEnergyStored(ForgeDirection from) {

		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}
	
	public boolean hasEnergyLeft() {
		return storage.getEnergyStored() > 0;
	}
	public void setEnergyStored(int energy) {
		storage.setEnergyStored(energy);
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int limit) {
		if (this.inventory[slot] != null) {
			ItemStack itemstack;
			if (this.inventory[slot].stackSize <= limit) {
				itemstack = this.inventory[slot];
				this.inventory[slot] = null;
				return itemstack;
			} else {
				itemstack = this.inventory[slot].splitStack(limit);

				if (this.inventory[slot].stackSize == 0) {
					this.inventory[slot] = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.inventory[slot] != null) {
			ItemStack itemstack = this.inventory[slot];
			this.inventory[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.inventory[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

	}


	@Override
	public String getInventoryName() {
		return this.machineName;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public abstract boolean isItemValidForSlot(int slot, ItemStack itemstack);

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slotsBottom : (side == 1 ? slotsTop : slotsSide);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		return this.isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		return Arrays.asList(this.getAccessibleSlotsFromSide(side)).contains(slot);
	}
	
	public void setMachineName(String name) {
		this.machineName = name;
	}
}
