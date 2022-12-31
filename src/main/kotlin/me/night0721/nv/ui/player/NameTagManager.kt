package me.night0721.nv.ui.player

import me.night0721.nv.database.RankDataManager
import me.night0721.nv.util.Rank
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import java.util.*

class NameTagManager {
    fun setNametags(player: Player) {
        val newScoreboard = Bukkit.getScoreboardManager().newScoreboard
        val obj = newScoreboard.registerNewObjective("TabList", Criteria.DUMMY, "Test")
        obj.displaySlot = DisplaySlot.PLAYER_LIST
        player.scoreboard = newScoreboard
        for (rank in Rank.values()) {
            val team = player.scoreboard.registerNewTeam(rank.name)
            team.prefix = rank.display + " "
        }
        for (target in Bukkit.getOnlinePlayers()) {
            if (player.uniqueId !== target.uniqueId) {
                val rank = RankDataManager.getRank(target.uniqueId)
                if (rank != null) player.scoreboard.getTeam(rank.name)!!.addEntry(target.name)
            }
        }
    }

    fun newTag(player: Player) {
        val rank = RankDataManager.getRank(player.uniqueId)
        for (target in Bukkit.getOnlinePlayers()) {
            target.scoreboard.getTeam(Objects.requireNonNullElse(rank, Rank.ROOKIE).name)!!.addEntry(player.name)
        }
    }

    fun removeTag(player: Player) {
        for (target in Bukkit.getOnlinePlayers()) {
            target.scoreboard.getEntryTeam(player.name)!!.removeEntry(player.name)
        }
    }
}