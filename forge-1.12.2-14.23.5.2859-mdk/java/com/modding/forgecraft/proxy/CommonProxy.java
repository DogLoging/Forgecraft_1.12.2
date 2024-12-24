package com.modding.forgecraft.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class CommonProxy
{
	public void preInit() {}
	public void init() {}
	public void postInit() {}
	
	@SuppressWarnings("deprecation")
	public static void registerEvent(Object evnet)
	{
		FMLCommonHandler.instance().bus().register(evnet);
		MinecraftForge.EVENT_BUS.register(evnet);
	}
}
