package com.modding.forgecraft.block.item;

import java.util.List;
import java.util.UUID;

import com.modding.forgecraft.Main;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemModArmor extends ItemArmor
{
	private String armorName;
	private int attackDamage, health, moveSpeed, defense;
	
	public ItemModArmor(String name, ArmorMaterial material, int attackDamage, int health, int moveSpeed, int defense, EntityEquipmentSlot slot)
	{
		super(material, 0, slot);
		
		this.armorName = name;
		
        this.attackDamage = attackDamage;
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.defense = defense;
	}
	
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
    	boolean leggings = this.getUnlocalizedName().contains("leggings");
    	String type1 = leggings ? "layer_2" : "layer_1";
    	
    	return Main.MODID + ":" + "textures/armor/" + this.armorName + "_" + type1 + ".png";
    }
    
    private boolean isFullArmorSet(EntityPlayer player)
    {
        ItemStack head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        ItemStack legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        ItemStack feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        
        return head.getItem() instanceof ItemModArmor && chest.getItem() instanceof ItemModArmor && legs.getItem() instanceof ItemModArmor && feet.getItem() instanceof ItemModArmor;
    }
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
    {
        if (!world.isRemote)
        {
            applyAttributes(player);
        }
    }

    private void applyAttributes(EntityPlayer player)
    {
        boolean hasFullSet = isFullArmorSet(player);

        if (hasFullSet)
        {
            double currentArmor = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).getBaseValue();
            double newArmorValue = currentArmor + this.defense;
            player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).setBaseValue(newArmorValue);
        	
            addAttributeModifier(player, SharedMonsterAttributes.ATTACK_DAMAGE, "ArmorAttackDamage", attackDamage, 0);
            addAttributeModifier(player, SharedMonsterAttributes.MAX_HEALTH, "ArmorMaxHealth", health, 0);
            addAttributeModifier(player, SharedMonsterAttributes.MOVEMENT_SPEED, "ArmorMoveSpeed", moveSpeed / 100.0, 1);
        }
        else
        {
            removeModifiers(player);
        }
    }

    private void addAttributeModifier(EntityPlayer player, IAttribute attribute, String name, double value, int operation)
    {
        IAttributeInstance instance = player.getEntityAttribute(attribute);

        if (instance != null && instance.getModifier(UUID.nameUUIDFromBytes(name.getBytes())) == null)
        {
            AttributeModifier modifier = new AttributeModifier(UUID.nameUUIDFromBytes(name.getBytes()), name, value, operation);
            instance.applyModifier(modifier);
        }
    }

    private void removeModifiers(EntityPlayer player)
    {
    	player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).setBaseValue(0.0);
    	
        for (IAttribute attribute : new IAttribute[]{SharedMonsterAttributes.ATTACK_DAMAGE, SharedMonsterAttributes.MAX_HEALTH, SharedMonsterAttributes.MOVEMENT_SPEED})
        {
            IAttributeInstance instance = player.getEntityAttribute(attribute);

            if (instance != null)
            {
                for (AttributeModifier modifier : instance.getModifiers())
                {
                    if (modifier.getName().startsWith("Armor"))
                    {
                        instance.removeModifier(modifier);
                    }
                }
            }
        }
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if (stack.getItem() instanceof ItemModArmor)
        {
            ItemModArmor armor = (ItemModArmor) stack.getItem();
            
            tooltip.add("§7Bonus full set: ");
            
            if (armor.attackDamage < 0)
            {
                tooltip.add("§cDamage: " + armor.attackDamage);
            }
            else if(armor.attackDamage != 0)
            {
            	tooltip.add("§9Damage: " + armor.attackDamage + "+");
            }
            
            if (armor.health < 0)
            {
                tooltip.add("§cHealth: " + armor.health);
            }
            else if(armor.health != 0)
            {
            	tooltip.add("§9Health: " + armor.health + "+");
            }
            
            if (armor.moveSpeed < 0)
            {
                tooltip.add("§cMovespeed: " + (armor.moveSpeed / 100.0) + "%");
            }
            else if(armor.moveSpeed != 0)
            {
            	tooltip.add("§9Movespeed: " + (armor.moveSpeed / 100.0) + "%");
            }
            
            if (armor.defense < 0)
            {
                tooltip.add("§cDefense: " + armor.defense);
            }
            else if(armor.defense != 0)
            {
            	tooltip.add("§9Defense: " + armor.defense + "+");
            }
        }
    }
}
