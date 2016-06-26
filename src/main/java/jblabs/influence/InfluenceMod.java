package jblabs.influence;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import jblabs.influence.proxy.CommonProxy;

@Mod(modid = InfluenceMod.MODID, version = InfluenceMod.VERSION)
public class InfluenceMod
{
    
    public static final String MODID = "influencemod";
    
    public static final String VERSION = "1.0";

    @Instance(MODID)
	public static InfluenceMod modInstance; 

    @SidedProxy(clientSide="jblabs.influence.proxy.ClientProxy", serverSide="jblabs.influence.proxy.ServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
