package me.night0721.nv.ui.player

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot

class BelowNameManager {
    fun setBelowName(player: Player) {
        val board = player.scoreboard
        val obj = board.registerNewObjective("HealthBar", Criteria.HEALTH, ChatColor.RED.toString() + "❤")
        obj.displaySlot = DisplaySlot.BELOW_NAME
        player.scoreboard = board
    }
}