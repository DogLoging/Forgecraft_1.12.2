package com.modding.forgecraft.register;

import com.modding.forgecraft.init.ModBlocks;
import com.modding.forgecraft.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModRegistryEvent
{
	@SubscribeEvent
	public void onRegisterBlockEvent(RegistryEvent.Register<Block> event)
	{
		ModBlocks.registerBlock(event.getRegistry());
	}
	
	@SubscribeEvent
	public void onRegisterItemEvent(RegistryEvent.Register<Item> event)
	{
		ModBlocks.registerItem(event.getRegistry());
		
		ModItems.registerItem = event.getRegistry();
		ModItems.initialization();
	}
}
