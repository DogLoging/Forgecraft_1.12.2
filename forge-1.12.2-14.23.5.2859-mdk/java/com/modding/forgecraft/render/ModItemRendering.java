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
		
		register("copper_helmet", ModItems.copper_helmet);
		register("copper_chestplate", ModItems.copper_chestplate);
		register("copper_leggings", ModItems.copper_leggings);
		register("copper_boots", ModItems.copper_boots);
		
		register("steel_helmet", ModItems.steel_helmet);
		register("steel_chestplate", ModItems.steel_chestplate);
		register("steel_leggings", ModItems.steel_leggings);
		register("steel_boots", ModItems.steel_boots);
		
		register("titanium_helmet", ModItems.titanium_helmet);
		register("titanium_chestplate", ModItems.titanium_chestplate);
		register("titanium_leggings", ModItems.titanium_leggings);
		register("titanium_boots", ModItems.titanium_boots);
		
		register("adamantium_helmet", ModItems.adamantium_helmet);
		register("adamantium_chestplate", ModItems.adamantium_chestplate);
		register("adamantium_leggings", ModItems.adamantium_leggings);
		register("adamantium_boots", ModItems.adamantium_boots);
	}
	
	public static void register(String name, Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Main.MODID + ":" + name, "inventory"));
	}
}
