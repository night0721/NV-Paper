package me.night0721.nv.ui.player

import io.papermc.paper.event.player.AsyncChatEvent
import me.night0721.nv.database.RankDataManager
import me.night0721.nv.database.UserDataManager
import me.night0721.nv.entities.miners.CryptoMiner
import me.night0721.nv.entities.npcs.NPCManager
import me.night0721.nv.util.Rank
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class ScoreboardListener : Listener {
    val sideBarManager: SideBarManager = SideBarManager()
    private val belowNameManager: BelowNameManager = BelowNameManager()
    companion object {
        val nameTagManager: NameTagManager = NameTagManager()
    }
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val player = e.player
        if (!player.hasPlayedBefore()) {
            e.player.setResourcePack("https://www.dropbox.com/s/h12f0b4z78bufgb/%C2%A7b%C2%A7lNKRP.zip?dl=0", "4bc4e51c9cd77eff3408d44642cc8c088527fc79")
            e.player.showTitle(
                Title.title(
                    Component.text()
                        .append(Component.text().content("Welcome to Vanadium!").color(NamedTextColor.RED).build())
                        .build(), Component.text().append(
                        Component.text().content("Hello!").color(NamedTextColor.GREEN).build()
                    ).build(), Title.Times.times(
                        java.time.Duration.ofSeconds(0),
                        java.time.Duration.ofSeconds(1),
                        java.time.Duration.ofSeconds(0)
                    )
                )
            )
            RankDataManager.setRank(player.uniqueId, Rank.ROOKIE)
            UserDataManager().createUserBank(e.player.uniqueId.toString())
        }
        e.player.sendPlayerListHeader(
            Component.text().append(Component.text().content("You are playing on ").color(NamedTextColor.AQUA).build())
                .append(
                    Component.text().content("127.0.0.1").color(NamedTextColor.GREEN).build()
                ).build()
        )
        e.player.sendPlayerListFooter(
            Component.text()
                .append(Component.text().content("Ranks, boosters, & more!").color(NamedTextColor.GOLD).build()).build()
        )
        nameTagManager.setNametags(player)
        nameTagManager.newTag(player)
        sideBarManager.setSideBar(player)
        sideBarManager.start(player)
        belowNameManager.setBelowName(player)
        val rank = RankDataManager.getRank(player.uniqueId)
        e.joinMessage(
            Component.text().append(Component.text().content(rank!!.display + " ").color(rank.color).build())
                .append(Component.text().content(player.name).color(rank.color).build())
                .append(Component.text().content(" joined the server!").color(NamedTextColor.WHITE).build()).build()
        )
        if (NPCManager.getNPCs().isEmpty()) return
        NPCManager.addJoinPacket(e.player)
        CryptoMiner.onJoin(e.player)
    }

    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        e.quitMessage(null)
        nameTagManager.removeTag(e.player)
        e.player.scoreboard = Bukkit.getScoreboardManager().newScoreboard
        val board = sideBarManager.board
        if (AnimatedSideBar.hasID(board!!)) board.stop()
    }

    @EventHandler
    fun onChat(e: AsyncChatEvent) {
        e.isCancelled = true
        val player = e.player
        val rank = RankDataManager.getRank(player.uniqueId)
        Bukkit.broadcast(
            Component.text().append(Component.text().content(rank!!.display + " ").color(rank.color).build())
                .append(Component.text().content(player.name).color(rank.color).build())
                .append(Component.text().content(": ").color(NamedTextColor.WHITE).build()).append(
                    Component.text().content((e.message() as TextComponent).content()).color(NamedTextColor.WHITE)
                        .build()
                ).build()
        )
    }
}