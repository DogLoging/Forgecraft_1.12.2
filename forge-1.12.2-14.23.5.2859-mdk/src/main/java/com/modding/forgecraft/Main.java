package com.modding.forgecraft;

import com.modding.forgecraft.init.ModBlocks;
import com.modding.forgecraft.proxy.CommonProxy;
import com.modding.forgecraft.register.ModRegistryEvent;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MODID, version = Main.VERSION, name = Main.NAME)
public class Main
{
	public static final String MODID = "forgecraft";
	public static final String VERSION = "1.0.0";
	public static final String NAME = "ForgeCraft";
	
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = "com.modding.forgecraft.proxy.ClientProxy", serverSide = "com.modding.forgecraft.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		ModBlocks.initialization();
		
		CommonProxy.registerEvent(new ModRegistryEvent());
		proxy.preInit();
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		proxy.init();
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();
	}
}
