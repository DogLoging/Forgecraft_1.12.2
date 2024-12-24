package com.modding.forgecraft.proxy;

import com.modding.forgecraft.render.ModBlockRendering;
import com.modding.forgecraft.render.ModItemRendering;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit() 
	{
		registerEvent(new ModItemRendering());
		registerEvent(new ModBlockRendering());
	}
	
	@Override
	public void init()
	{
		
	}
	
	@Override
	public void postInit()
	{
		
	}
}
