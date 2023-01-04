package me.night0721.nv.ui.player

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot

class BelowNameManager {
    fun setBelowName(player: Player) {
        val board = player.scoreboard
        val p = board.getObjective("P")
        if (p == null) {
            val objective = board.registerNewObjective("P", Criteria.DUMMY, Component.text("P", NamedTextColor.RED))
            objective.displaySlot = DisplaySlot.BELOW_NAME
            val score = objective.getScore(player.name)
            score.score = 19
        }
    }
}