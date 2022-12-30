package me.night0721.nv.util.enchantments

enum class Enchantments(private val namespacekey: String, private val enchantname: String, private val maxLevel: Int) {
    THUNDERBOLT("thunderbolt", "ThunderBolt", 5),
    SMELTING_TOUCH("smelting-touch", "Smelting Touch", 1);

    fun getNamespacekey(): String {
        return namespacekey
    }

    fun getName(): String {
        return enchantname
    }

    fun getMaxLevel(): Int {
        return maxLevel
    }
}