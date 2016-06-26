package jblabs.influence.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import jblabs.influence.InfluenceMod;
import jblabs.influence.handler.GuiHandler;
import jblabs.influence.machine.MachineRegistry;
import jblabs.influence.tile.TileMachine2Slot;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
    	System.out.println("hello preinit");
    	GameRegistry.registerTileEntity(TileMachine2Slot.class, "TileMachine2Slot");
    }

    public void init(FMLInitializationEvent e) {
    	NetworkRegistry.INSTANCE.registerGuiHandler(InfluenceMod.modInstance, GuiHandler.handler()); 
    	MachineRegistry.registry().registerGUIS();
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}
