package com.modding.forgecraft.init;

import com.modding.forgecraft.Main;
import com.modding.forgecraft.items.ItemModArmor;
import com.modding.forgecraft.items.ItemWeaponSword;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
	public static Item copper_ingot, steel_ingot, titanium_ingot, adamantium_ingot;
	
	public static Item copper_sword, steel_sword, titanium_sword, adamantium_sword;
	
	public static Item copper_helmet, copper_chestplate, copper_leggings, copper_boots;
	
	public static Item steel_helmet, steel_chestplate, steel_leggings, steel_boots;
	
	public static Item titanium_helmet, titanium_chestplate, titanium_leggings, titanium_boots;
	
	public static Item adamantium_helmet, adamantium_chestplate, adamantium_leggings, adamantium_boots;
	
	public static IForgeRegistry<Item> registerItem;
	
	public static void initialization()
	{
		copper_ingot = register("copper_ingot", new Item().setCreativeTab(CreativeTabs.MATERIALS));
		steel_ingot = register("steel_ingot", new Item().setCreativeTab(CreativeTabs.MATERIALS));
		titanium_ingot = register("titanium_ingot", new Item().setCreativeTab(CreativeTabs.MATERIALS));
		adamantium_ingot = register("adamantium_ingot", new Item().setCreativeTab(CreativeTabs.MATERIALS));
				
		// attackDamage, health, moveSpeed, defense.
		copper_sword = register("copper_sword", new ItemWeaponSword(ToolMaterial.STONE, 3.0F, 240.0F, 250));
		copper_helmet = register("copper_helmet", new ItemModArmor("copper_armor", ArmorMaterial.CHAIN, 4, -2, 45, 0, EntityEquipmentSlot.HEAD));
		copper_chestplate = register("copper_chestplate", new ItemModArmor("copper_armor", ArmorMaterial.CHAIN, 4, -2, 45, 0, EntityEquipmentSlot.CHEST));
		copper_leggings = register("copper_leggings", new ItemModArmor("copper_armor", ArmorMaterial.CHAIN, 4, -2, 45, 0, EntityEquipmentSlot.LEGS));
		copper_boots = register("copper_boots", new ItemModArmor("copper_armor", ArmorMaterial.CHAIN, 4, -2, 45, 0, EntityEquipmentSlot.FEET));
	
		steel_sword = register("steel_sword", new ItemWeaponSword(ToolMaterial.IRON, 3.0F, 0.0F, 1561));
		steel_helmet = register("steel_helmet", new ItemModArmor("steel_armor", ArmorMaterial.DIAMOND, 0, 28, -25, 4, EntityEquipmentSlot.HEAD));
		steel_chestplate = register("steel_chestplate", new ItemModArmor("steel_armor", ArmorMaterial.DIAMOND, 0, 28, -25, 4, EntityEquipmentSlot.CHEST));
		steel_leggings = register("steel_leggings", new ItemModArmor("steel_armor", ArmorMaterial.DIAMOND, 0, 28, -25, 4, EntityEquipmentSlot.LEGS));
		steel_boots = register("steel_boots", new ItemModArmor("steel_armor", ArmorMaterial.DIAMOND, 0, 28, -25, 4, EntityEquipmentSlot.FEET));
		
		titanium_sword = register("titanium_sword", new ItemWeaponSword(ToolMaterial.DIAMOND, 4.0F, 240.0F, 1561));
		titanium_helmet = register("titanium_helmet", new ItemModArmor("titanium_armor", ArmorMaterial.IRON, 6, -4, 55, 0, EntityEquipmentSlot.HEAD));
		titanium_chestplate = register("titanium_chestplate", new ItemModArmor("titanium_armor", ArmorMaterial.IRON, 6, -4, 55, 0, EntityEquipmentSlot.CHEST));
		titanium_leggings = register("titanium_leggings", new ItemModArmor("titanium_armor", ArmorMaterial.IRON, 6, -4, 55, 0, EntityEquipmentSlot.LEGS));
		titanium_boots = register("titanium_boots", new ItemModArmor("titanium_armor", ArmorMaterial.IRON, 6, -4, 55, 0, EntityEquipmentSlot.FEET));
		
		adamantium_sword = register("adamantium_sword", new ItemWeaponSword(ToolMaterial.DIAMOND, 6.0F, 0.0F, 3122));
		adamantium_helmet = register("adamantium_helmet", new ItemModArmor("adamantium_armor", ArmorMaterial.DIAMOND, 0, 32, -20, 6, EntityEquipmentSlot.HEAD));
		adamantium_chestplate = register("adamantium_chestplate", new ItemModArmor("adamantium_armor", ArmorMaterial.DIAMOND, 0, 32, -20, 6, EntityEquipmentSlot.CHEST));
		adamantium_leggings = register("adamantium_leggings", new ItemModArmor("adamantium_armor", ArmorMaterial.DIAMOND, 0, 32, -20, 6, EntityEquipmentSlot.LEGS));
		adamantium_boots = register("adamantium_boots", new ItemModArmor("adamantium_armor", ArmorMaterial.DIAMOND, 0, 32, -20, 6, EntityEquipmentSlot.FEET));
	}
	
	public static Item register(String name, Item item)
	{
		item.setUnlocalizedName(name);
		registerItem.register(item.setRegistryName(Main.MODID + ":" + name));
		
		return item;
	}
}
