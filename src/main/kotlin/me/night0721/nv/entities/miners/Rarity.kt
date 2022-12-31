package me.night0721.nv.entities.miners

import net.md_5.bungee.api.ChatColor

enum class Rarity(val display: String, val color: String) {
    COMMON(
        org.bukkit.ChatColor.WHITE.toString() + org.bukkit.ChatColor.BOLD + "COMMON",
        org.bukkit.ChatColor.WHITE.toString()
    ),
    UNCOMMON(
        ChatColor.of("#31ff09").toString() + org.bukkit.ChatColor.BOLD + "UNCOMMON", ChatColor.of("#31ff09").toString()
    ),
    RARE(
        ChatColor.of("#2f57ae").toString() + org.bukkit.ChatColor.BOLD + "RARE", ChatColor.of("#2f57ae").toString()
    ),
    EPIC(
        ChatColor.of("#b201b2").toString() + org.bukkit.ChatColor.BOLD + "EPIC", ChatColor.of("#b201b2").toString()
    ),
    LEGENDARY(
        ChatColor.of("#ffa21b").toString() + org.bukkit.ChatColor.BOLD + "LEGENDARY", ChatColor.of("#ffa21b").toString()
    ),
    MYTHIC(
        ChatColor.of("#ff23ff").toString() + org.bukkit.ChatColor.BOLD + "MYTHIC", ChatColor.of("#ff23ff").toString()
    ),
    ULTRA(
        org.bukkit.ChatColor.RED.toString() + org.bukkit.ChatColor.BOLD + "ULTRA",
        org.bukkit.ChatColor.RED.toString()
    ),
    GRAND(
        ChatColor.of("#00fdff").toString() + org.bukkit.ChatColor.BOLD + "GRAND", ChatColor.of("#00fdff").toString()
    );

    companion object {
        fun getRarity(str: String?): Rarity {
            return when (str) {
                "UNCOMMON" -> UNCOMMON
                "RARE" -> RARE
                "EPIC" -> EPIC
                "LEGENDARY" -> LEGENDARY
                "MYTHIC" -> MYTHIC
                "ULTRA" -> ULTRA
                "GRAND" -> GRAND
                else -> COMMON
            }
        }
    }
}