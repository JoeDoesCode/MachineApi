package jblabs.influence.block;

import jblabs.influence.recipe.MachineRecipeHandler;
import jblabs.influence.tile.TileMachine2Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockStatedMachine2Slot extends BlockStatedMachine {

	public BlockStatedMachine2Slot(boolean isActive, String name, String texturebase, MachineRecipeHandler recipes) {
		super(isActive, name, texturebase);
		this.recipes = recipes;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		TileMachine2Slot t = new TileMachine2Slot();
		t.setMachineName(name);
		t.recipes = this.recipes;
		return t;
	}

}
