package me.night0721.nv.entities.items

import me.night0721.nv.entities.miners.Rarity
import org.bukkit.Material

enum class Items(
    val itemName: String, val weight: Double, val rarity: Rarity, val material: Material, val slot: Int
) {
    ETERNALSTARE("Eternal Stare", 29.0, Rarity.LEGENDARY, Material.COAL, 10),  // legendary charm
    MORNINGTEA("Morning Tea", 28.0, Rarity.EPIC, Material.IRON_INGOT, 12),  // epic emote
    PARACHUTE("Parachute - Doomed Chorus", 11.0, Rarity.EPIC, Material.GOLD_INGOT, 14),  // epic parachute
    RALLYCAR("Rally Car - Doomed Chorus", 10.0, Rarity.EPIC, Material.REDSTONE, 16),  //epic backpack
    WORLDAFLAME("World Aflame", 6.5, Rarity.LEGENDARY, Material.LAPIS_LAZULI, 28),  // legendary background
    MOLOTOVCOTAIL("Molotov Cotail - Soul Flame", 5.5, Rarity.EPIC, Material.COPPER_INGOT, 30),  // epic throwable
    KATANA("Katana - Silent Echo", 4.67, Rarity.EPIC, Material.EMERALD, 32),  // epic melee
    DLQ33("DL Q33 - Doomed Chorus", 4.0, Rarity.EPIC, Material.QUARTZ, 34),  // epic gun
    DAME("Dame - Shot Caller", 1.25, Rarity.EPIC, Material.DIAMOND, 19),  // character epic
    KILO141("Kilo 141 - Demonsong", 0.08, Rarity.LEGENDARY, Material.NETHERITE_INGOT, 25); // weapon legendary

    companion object {
        fun getByName(name: String?): Items? {
            for (item in values()) {
                if (item.itemName.equals(name, ignoreCase = true)) {
                    return item
                }
            }
            return null
        }
    }
}