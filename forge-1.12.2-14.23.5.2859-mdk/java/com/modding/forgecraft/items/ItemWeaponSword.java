package com.modding.forgecraft.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemSword;

public class ItemWeaponSword extends ItemSword
{
	public int durability;
	public float damage;
	public float speed;
	
	public ItemWeaponSword(ToolMaterial material, float damage, float speed, int durability)
	{
		super(material);
		
		this.damage = damage + material.getAttackDamage();
		this.speed = speed;
		
		this.durability = durability;
		this.setMaxDamage(this.durability);
	}
	
	@Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.damage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (this.speed / 100) -2.4000000953674316D, 0));
        }

        return multimap;
    }
}
