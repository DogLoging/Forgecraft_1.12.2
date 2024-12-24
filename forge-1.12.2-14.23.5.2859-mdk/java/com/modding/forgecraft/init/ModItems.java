package com.modding.forgecraft.init;

import com.modding.forgecraft.Main;
import com.modding.forgecraft.items.ItemWeaponSword;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
	public static Item copper_ingot, steel_ingot, titanium_ingot, adamantium_ingot;
	
	public static Item copper_sword, steel_sword, titanium_sword, adamantium_sword;
	
	public static IForgeRegistry<Item> registerItem;
	
	public static void initialization()
	{
		copper_ingot = register("copper_ingot", new Item().setCreativeTab(CreativeTabs.MATERIALS));
		steel_ingot = register("steel_ingot", new Item().setCreativeTab(CreativeTabs.MATERIALS));
		titanium_ingot = register("titanium_ingot", new Item().setCreativeTab(CreativeTabs.MATERIALS));
		adamantium_ingot = register("adamantium_ingot", new Item().setCreativeTab(CreativeTabs.MATERIALS));
	
		copper_sword = register("copper_sword", new ItemWeaponSword(ToolMaterial.STONE, 3.0F, 250));
		steel_sword = register("steel_sword", new ItemWeaponSword(ToolMaterial.IRON, 3.0F, 1561));
		titanium_sword = register("titanium_sword", new ItemWeaponSword(ToolMaterial.DIAMOND, 4.0F, 1561));
		adamantium_sword = register("adamantium_sword", new ItemWeaponSword(ToolMaterial.DIAMOND, 6.0F, 3122));
	}
	
	public static Item register(String name, Item item)
	{
		item.setUnlocalizedName(name);
		registerItem.register(item.setRegistryName(Main.MODID + ":" + name));
		
		return item;
	}
}
