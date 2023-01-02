package me.night0721.nv.util

import net.kyori.adventure.text.format.NamedTextColor

enum class Rank(val display: String, val color: NamedTextColor) {
    OWNER("<OWNER>", NamedTextColor.DARK_RED),
    ADMIN("<ADMIN>", NamedTextColor.RED),
    SPECIAL("<SPECIAL>", NamedTextColor.GOLD),
    ROOKIE("<ROOKIE>", NamedTextColor.DARK_GREEN);
}