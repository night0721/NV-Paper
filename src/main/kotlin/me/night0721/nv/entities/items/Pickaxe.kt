package me.night0721.nv.entities.items

import com.google.common.collect.ArrayListMultimap
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Pickaxe(item: ItemStack) {
    val multimap: ArrayListMultimap<Material?, Material?> = ArrayListMultimap.create()
    private val phases = HashMap<Material, Long>()
    private val itemStack: ItemStack

    init {
        multimap.put(Material.STONE_PICKAXE, Material.IRON_ORE) //put some blocks and pickaxe to mine
        multimap.put(Material.STONE_PICKAXE, Material.DIAMOND_ORE)
        phases[Material.DIAMOND_ORE] = 40L
        phases[Material.IRON_ORE] = 30L
        itemStack = item
    }

    fun getMiningPerPhase(material: Material): Long {
        return phases[material]!!
    }

    val material: Material
        get() = itemStack.type
}