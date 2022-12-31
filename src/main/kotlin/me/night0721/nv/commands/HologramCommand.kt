package me.night0721.nv.commands

import me.night0721.nv.entities.holograms.PerPlayerHologram
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HologramCommand : Command("hologram", arrayOf(), "Spawn a hologram", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            val ar = arrayOf(
                ChatColor.AQUA.toString() + "Hi",
                ChatColor.DARK_PURPLE.toString() + "What",
                ChatColor.GOLD.toString() + "Hello World",
                ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "CLICK"
            )
            PerPlayerHologram(sender, ar)
        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<String>): MutableList<String> {
        return ArrayList()
    }
}