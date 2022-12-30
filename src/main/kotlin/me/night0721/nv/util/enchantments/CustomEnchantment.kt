package me.night0721.nv.util.enchantments

import io.papermc.paper.enchantments.EnchantmentRarity
import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.entity.EntityCategory
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

class CustomEnchantment(namespace: String?, private val name: String, private val max: Int) : Enchantment(
    NamespacedKey.minecraft(
        namespace!!
    )
) {
    @Deprecated("Deprecated in Java")
    override fun getName(): String {
        return name
    }

    override fun getMaxLevel(): Int {
        return max
    }

    override fun getStartLevel(): Int {
        return 1
    }

    override fun getItemTarget(): EnchantmentTarget {
        return EnchantmentTarget.WEAPON
    }

    override fun isTreasure(): Boolean {
        return false
    }

    override fun isCursed(): Boolean {
        return false
    }

    override fun conflictsWith(other: Enchantment): Boolean {
        return false
    }

    override fun canEnchantItem(item: ItemStack): Boolean {
        return false
    }

    override fun displayName(level: Int): Component {
        return Component.text(name)
    }

    override fun isTradeable(): Boolean {
        return false
    }

    override fun isDiscoverable(): Boolean {
        return false
    }

    override fun getRarity(): EnchantmentRarity {
        return EnchantmentRarity.VERY_RARE
    }

    override fun getDamageIncrease(level: Int, entityCategory: EntityCategory): Float {
        return 0.0f
    }

    override fun getActiveSlots(): Set<EquipmentSlot> {
        return mutableSetOf(
            EquipmentSlot.HAND,
            EquipmentSlot.OFF_HAND,
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
        )
    }

    override fun translationKey(): String {
        return ""
    }

}