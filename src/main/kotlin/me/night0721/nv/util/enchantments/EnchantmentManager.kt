package me.night0721.nv.util.enchantments

import org.bukkit.enchantments.Enchantment
import java.util.*

object EnchantmentManager {
    val enchants: MutableList<Enchantment?> = ArrayList()
    fun register() {
        val registeredList: MutableList<Boolean> = ArrayList()
        for (enchantment in Enchantments.values()) {
            val en: Enchantment =
                CustomEnchantment(enchantment.getNamespacekey(), enchantment.getName(), enchantment.getMaxLevel())
            enchants.add(en)
            registeredList.add(Arrays.stream(Enchantment.values()).toList().contains(en))
        }
        for (counter in registeredList.indices) {
            if (!registeredList[counter]) registerEnchantment(enchants[counter])
        }
    }

    private fun registerEnchantment(en: Enchantment?) {
        try {
            try {
                val f = Enchantment::class.java.getDeclaredField("acceptingNew")
                f.isAccessible = true
                f[null] = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Enchantment.registerEnchantment(en!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}