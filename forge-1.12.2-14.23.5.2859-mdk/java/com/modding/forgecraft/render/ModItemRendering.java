package com.modding.forgecraft.render;

import com.modding.forgecraft.Main;
import com.modding.forgecraft.init.ModItems;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModItemRendering
{
	@SubscribeEvent
	public void onModelRegisterEvent(ModelRegistryEvent event)
	{
		register("copper_ingot", ModItems.copper_ingot);
		register("steel_ingot", ModItems.steel_ingot);
		register("titanium_ingot", ModItems.titanium_ingot);
		register("adamantium_ingot", ModItems.adamantium_ingot);
		
		register("copper_sword", ModItems.copper_sword);
		register("steel_sword", ModItems.steel_sword);
		register("titanium_sword", ModItems.titanium_sword);
		register("adamantium_sword", ModItems.adamantium_sword);
	}
	
	public static void register(String name, Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Main.MODID + ":" + name, "inventory"));
	}
}
