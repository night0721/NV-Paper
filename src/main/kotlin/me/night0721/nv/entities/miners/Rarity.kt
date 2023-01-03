package me.night0721.nv.entities.miners

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

enum class Rarity(val display: TextComponent, val color: TextColor, val hex: String) {

    COMMON(
        Component.text().content("COMMON").color(TextColor.fromHexString("#ffffff"))
            .decoration(TextDecoration.BOLD, true).build(), TextColor.fromHexString("#ffffff")!!, "#ffffff"
    ),
    UNCOMMON(
        Component.text().content("UNCOMMON").color(TextColor.fromHexString("#31ff09"))
            .decoration(TextDecoration.BOLD, true).build(), TextColor.fromHexString("#31ff09")!!, "#31ff09"
    ),
    RARE(
        Component.text().content("RARE").color(TextColor.fromHexString("#2f57ae")).decoration(TextDecoration.BOLD, true)
            .build(), TextColor.fromHexString("#2f57ae")!!, "#2f57ae"
    ),
    EPIC(
        Component.text().content("EPIC").color(TextColor.fromHexString("#b201b2")).decoration(TextDecoration.BOLD, true)
            .build(), TextColor.fromHexString("#b201b2")!!, "#b201b2"

    ),
    LEGENDARY(
        Component.text().content("LEGENDARY").color(TextColor.fromHexString("#ffa21b"))
            .decoration(TextDecoration.BOLD, true).build(), TextColor.fromHexString("#ffa21b")!!, "#ffa21b"

    ),
    MYTHIC(
        Component.text().content("MYTHIC").color(TextColor.fromHexString("#ff23ff"))
            .decoration(TextDecoration.BOLD, true).build(), TextColor.fromHexString("#ff23ff")!!, "#ff23ff"

    ),
    ULTRA(
        Component.text().content("ULTRA").color(TextColor.fromHexString("#ea8888"))
            .decoration(TextDecoration.BOLD, true).build(), TextColor.fromHexString("#ea8888")!!, "#ea8888"
    ),
    GRAND(
        Component.text().content("GRAND").color(TextColor.fromHexString("#00fdff"))
            .decoration(TextDecoration.BOLD, true).build(), TextColor.fromHexString("#00fdff")!!, "#00fdff"

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