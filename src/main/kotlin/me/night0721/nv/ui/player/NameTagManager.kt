package me.night0721.nv.ui.player

import me.night0721.nv.database.RankDataManager
import me.night0721.nv.util.Rank
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import java.util.*

class NameTagManager {
    fun setNametags(player: Player) {
        val newScoreboard = Bukkit.getScoreboardManager().newScoreboard
        val obj = newScoreboard.registerNewObjective("TabList", Criteria.DUMMY, Component.text().content(" ").build())
        obj.displaySlot = DisplaySlot.PLAYER_LIST
        player.scoreboard = newScoreboard
        for (rank in Rank.values()) {
            val team = player.scoreboard.registerNewTeam(rank.display)
            team.prefix(Component.text().content(rank.display + " ").color(rank.color).build())
        }
        for (target in Bukkit.getOnlinePlayers()) {
            if (player.uniqueId !== target.uniqueId) {
                val rank = RankDataManager.getRank(target.uniqueId)
                if (rank != null) player.scoreboard.getTeam(rank.display)!!.addEntry(target.name)
            }
        }
    }

    fun newTag(player: Player) {
        val rank = RankDataManager.getRank(player.uniqueId)
        for (target in Bukkit.getOnlinePlayers()) {
            Objects.requireNonNullElse(rank, Rank.ROOKIE)?.let { target.scoreboard.getTeam(it.display) }!!.addEntry(player.name)
        }
    }

    fun removeTag(player: Player) {
        for (target in Bukkit.getOnlinePlayers()) {
            target.scoreboard.getEntryTeam(player.name)!!.removeEntry(player.name)
        }
    }
}