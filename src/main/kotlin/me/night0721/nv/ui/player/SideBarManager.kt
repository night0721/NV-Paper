package me.night0721.nv.ui.player

import me.night0721.nv.NullValkyrie
import me.night0721.nv.database.UserDataManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Team
import java.util.*

class SideBarManager {
    private var taskID = 0
    var board: AnimatedSideBar? = null
    fun setSideBar(player: Player) {
        val board = player.scoreboard
        val obj: Objective? =
            if (board.getObjective("Vanadium") != null) board.getObjective("Vanadium") else board.registerNewObjective(
                "Vanadium",
                Criteria.DUMMY,
                Component.text().content(">> Vanadium <<").color(NamedTextColor.AQUA)
                    .decoration(TextDecoration.BOLD, true).build()
            )
        obj!!.displaySlot = DisplaySlot.SIDEBAR
        val hypens = obj.getScore(ChatColor.GOLD.toString() + "=-=-=-=-=-=-=-=")
        hypens.score = 9
        val name = obj.getScore(ChatColor.BLUE.toString() + "Player Name: ")
        name.score = 8
        val name2 = obj.getScore(ChatColor.WHITE.toString() + player.name)
        name2.score = 7
        val space1 = obj.getScore("  ")
        space1.score = 6
        val players = obj.getScore(ChatColor.LIGHT_PURPLE.toString() + "Players Online:")
        players.score = 5
        val playercount = obj.getScore(ChatColor.YELLOW.toString() + Bukkit.getServer().onlinePlayers.size)
        playercount.score = 4
        val space2 = obj.getScore(" ")
        space2.score = 2
        val website = obj.getScore(ChatColor.YELLOW.toString() + "cath.js.org")
        website.score = 1
        val bankTeam: Team? =
            if (board.getTeam("Bank") != null) board.getTeam("Bank") else board.registerNewTeam("Bank")
        bankTeam!!.addEntry(ChatColor.BOLD.toString())
        bankTeam.prefix(
            Component.text().content("Bank: ").color(NamedTextColor.BLUE).build()
        )
        bankTeam.suffix(
            Component.text().content(UserDataManager().getUser(player.uniqueId.toString())!!["Bank"].toString())
                .color(NamedTextColor.YELLOW).build()
        )
        obj.getScore(ChatColor.BOLD.toString()).score = 3
        player.scoreboard = board
    }

    fun start(player: Player) {
        board = AnimatedSideBar(player.uniqueId)
        taskID = Bukkit.getScheduler()
            .scheduleSyncRepeatingTask(NullValkyrie.getPlugin(NullValkyrie::class.java)!!, object : Runnable {
                var count = 0
                fun animate(str: String?) {
                    val board = player.scoreboard
                    val objective: Objective? =
                        if (board.getObjective("Vanadium") != null) board.getObjective("Vanadium") else board.registerNewObjective(
                            "Vanadium", Criteria.DUMMY, Component.text().content(">> Vanadium <<").color(NamedTextColor.AQUA)
                                .decoration(TextDecoration.BOLD, true).build()
                        )
                    objective!!.displaySlot = DisplaySlot.SIDEBAR
                    objective.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize(str!!))
                }

                override fun run() {
                    if (!AnimatedSideBar.hasID(board!!)) board!!.setID(taskID)
                    if (count == 13) count = 0
                    when (count) {
                        0 -> animate("&1&l>> &e&lVanadium&1&l <<")
                        1 -> animate("&b&l>&1&l> &e&lVanadium &1&l<<")
                        2 -> animate("&1&l>&b&l> &e&lVanadium &1&l<<")
                        3 -> animate("&1&l>> &b&lV&e&lanadium&1&l <<")
                        4 -> animate("&1&l>> &e&lV&b&la&e&lnadium&1&l <<")
                        5 -> animate("&1&l>> &e&lVa&b&ln&e&ladium&1&l <<")
                        6 -> animate("&1&l>> &e&lVan&b&la&e&ldium&1&l <<")
                        7 -> animate("&1&l>> &e&lVana&b&ld&e&lium&1&l <<")
                        8 -> animate("&1&l>> &e&lVanad&b&li&e&lum&1&l <<")
                        9 -> animate("&1&l>> &e&lVanadi&b&lu&e&lm&1&l <<")
                        10 -> animate("&1&l>> &e&lVanadiu&b&lm&1&l <<")
                        11 -> animate("&1&l>> &e&lVanadium &b&l<&1&l<")
                        12 -> {
                            animate("&1&l>> &e&lVanadium &1&l<&b&l<")
                            setSideBar(player)
                        }
                    }
                    count++
                }
            }, 0, 10)
    }

    fun addBank(uuid: String?, amount: Int) {
        val uid = UUID.fromString(uuid)
        Bukkit.getPlayer(uid)!!.scoreboard.getTeam("Bank")!!.suffix(
            Component.text().content(UserDataManager().getUser(uuid)!!["Bank"].toString()).color(NamedTextColor.YELLOW)
                .append(Component.text().content("+($amount)").color(NamedTextColor.WHITE).build()).build()
        )
    }
}