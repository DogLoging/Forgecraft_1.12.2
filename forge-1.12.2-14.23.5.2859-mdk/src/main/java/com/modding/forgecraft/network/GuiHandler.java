package com.modding.forgecraft.network;

import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.modding.forgecraft.Main;
import com.modding.forgecraft.inventory.ContainerFusionFurnace;
import com.modding.forgecraft.inventory.GuiFusionFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	private final Table<ItemStack, ItemStack, ItemStack> fusionRecipeList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

        if (tileEntity != null)
        {
            if (ID == Main.GUI_ENUM.FUSION.ordinal())
            {
                return new ContainerFusionFurnace(player.inventory, (IInventory)tileEntity);
            }
        }
        
		return null;
	}
	
	public void addFusionRecipe(ItemStack slot_1, ItemStack slot_2, ItemStack result, float exp)
	{
		this.fusionRecipeList.put(slot_1, slot_2, result);
		this.experienceList.put(result, Float.valueOf(exp));
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

        if (tileEntity != null)
        {
            if (ID == Main.GUI_ENUM.FUSION.ordinal())
            {
                return new GuiFusionFurnace(player.inventory, (IInventory)tileEntity);
            }
        }
        
		return null;
	}
	
}
