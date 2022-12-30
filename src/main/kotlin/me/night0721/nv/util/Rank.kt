package me.night0721.nv.util

import net.kyori.adventure.text.format.NamedTextColor

enum class Rank(val display: String, val color: NamedTextColor) {
    OWNER("<OWNER>", NamedTextColor.DARK_RED),
    ADMIN("<ADMIN>", NamedTextColor.RED),
    SPECIAL("<SPECIAL>", NamedTextColor.GOLD),
    ROOKIE("<ROOKIE>", NamedTextColor.DARK_GREEN);

    companion object {
        fun getRank(rank: String): Rank {
            return when (rank) {
                "OWNER" -> OWNER
                "ADMIN" -> ADMIN
                "SPECIAL" -> SPECIAL
                "ROOKIE" -> ROOKIE
                else -> ROOKIE
            }
        }

        fun getDisplay(rank: String): String {
            return getRank(rank).display
        }
    }
}