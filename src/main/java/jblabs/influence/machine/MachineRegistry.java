package jblabs.influence.machine;

import java.util.ArrayList;

import jblabs.influence.handler.GuiHandler;

public class MachineRegistry {
	private static MachineRegistry handler = new MachineRegistry();
	private ArrayList<Machine> machines = new ArrayList<Machine>();
	
	public static MachineRegistry registry() {
		return handler;
	}
	
	public void registerMachine(Machine mach) {
		machines.add(mach);
	}
	
	public void registerGUIS() {
		for (Machine mach: machines) {
            mach.registerGUI(GuiHandler.handler());
        }
	}
	public void preInit()  {
		for (Machine mach: machines) {
            mach.preInit();
        }
	}
	
	
}
