package me.night0721.nv.ui.player

import me.night0721.nv.NullValkyrie
import me.night0721.nv.database.UserDataManager
import me.night0721.nv.util.Util.color
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
        val obj: Objective?
        obj =
            if (board.getObjective("Vanadium") != null) board.getObjective("Vanadium") else board.registerNewObjective(
                "Vanadium",
                Criteria.DUMMY,
                ChatColor.AQUA.toString() + ChatColor.BOLD + ">> Vanadium <<"
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
        val bankTeam: Team?
        bankTeam = if (board.getTeam("Bank") != null) board.getTeam("Bank") else board.registerNewTeam("Bank")
        bankTeam!!.addEntry(ChatColor.BOLD.toString())
        bankTeam.prefix = ChatColor.BLUE.toString() + "Bank: "
        bankTeam.suffix =
            ChatColor.YELLOW.toString() + UserDataManager().getUser(player.uniqueId.toString())!!["Bank"].toString()
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
                    val objective: Objective?
                    objective =
                        if (board.getObjective("Vanadium") != null) board.getObjective("Vanadium") else board.registerNewObjective(
                            "Vanadium",
                            Criteria.DUMMY,
                            ChatColor.AQUA.toString() + ChatColor.BOLD + ">> Vanadium <<"
                        )
                    objective!!.displaySlot = DisplaySlot.SIDEBAR
                    objective.displayName = color(str)
                }

                override fun run() {
                    if (!board!!.hasID()) board!!.setID(taskID)
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
        Bukkit.getPlayer(uid)!!.scoreboard.getTeam("Bank")!!.suffix =
            ChatColor.YELLOW.toString() + UserDataManager().getUser(uuid)!!["Bank"] + ChatColor.WHITE + "+(" + amount + ")"
    }
}