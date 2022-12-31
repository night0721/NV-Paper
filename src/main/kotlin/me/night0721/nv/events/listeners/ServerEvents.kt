package me.night0721.nv.events.listeners

import me.night0721.nv.packets.handle.PacketInjector
import me.night0721.nv.util.Util.centerText
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerResourcePackStatusEvent
import org.bukkit.event.server.ServerListPingEvent
import org.bukkit.event.weather.WeatherChangeEvent
import java.io.File

class ServerEvents : Listener {
    val bossbar: BossBar
    val injector: PacketInjector

    init {
        bossbar = Bukkit.createBossBar(ChatColor.GOLD.toString() + "Kuudra", BarColor.RED, BarStyle.SEGMENTED_12)
        injector = PacketInjector()
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        bossbar.addPlayer(e.player)
        injector.addPlayer(e.player)
    }

    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        injector.removePlayer(e.player)
    }

    @EventHandler
    fun onPing(e: ServerListPingEvent) {
        e.maxPlayers = 8964
        val s = centerText("Vanadium", 45)
        val s2 = centerText("Support 1.19.3", 45)
        e.motd = """
            ${ChatColor.AQUA}${ChatColor.BOLD}$s
            ${ChatColor.GOLD}${ChatColor.BOLD}$s2
            """.trimIndent()
        try {
            e.setServerIcon(Bukkit.loadServerIcon(File("nuke.png")))
        } catch (ee: Exception) {
            ee.printStackTrace()
        }
    }

    @EventHandler
    fun onWeatherChange(e: WeatherChangeEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun onResourcePackChange(e: PlayerResourcePackStatusEvent) {
        if (e.status == PlayerResourcePackStatusEvent.Status.DECLINED || e.status == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD) {
            e.player.kickPlayer("You must download the resource pack to play on this server!")
        }
    }
}