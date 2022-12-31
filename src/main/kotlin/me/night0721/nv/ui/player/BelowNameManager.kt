package me.night0721.nv.ui.player

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot

class BelowNameManager {
    fun setBelowName(player: Player) {
        val board = player.scoreboard
        val obj = board.registerNewObjective("HealthBar", Criteria.HEALTH, Component.text().content("❤").color(
            NamedTextColor.RED).build())
        obj.displaySlot = DisplaySlot.BELOW_NAME
        player.scoreboard = board
    }
}