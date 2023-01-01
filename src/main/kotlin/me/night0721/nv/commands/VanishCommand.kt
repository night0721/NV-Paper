package me.night0721.nv.commands

import me.night0721.nv.NullValkyrie
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class VanishCommand : Command("vanish", arrayOf(), "Turn yourself into invisible", "") {
    private val vanished: MutableList<UUID> = ArrayList()
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            if (vanished.contains(sender.uniqueId)) {
                vanished.remove(sender.uniqueId)
                for (target in Bukkit.getOnlinePlayers()) {
                    target.showPlayer(NullValkyrie.getPlugin(NullValkyrie::class.java)!!, sender)
                }
                sender.sendMessage(ChatColor.GREEN.toString() + "You are now seen by people")
            } else {
                vanished.add(sender.uniqueId)
                for (target in Bukkit.getOnlinePlayers()) {
                    target.hidePlayer(NullValkyrie.getPlugin(NullValkyrie::class.java)!!, sender)
                }
                sender.sendMessage(ChatColor.GREEN.toString() + "You are now vanished")
            }
        }
    }

    override fun CommandSender?.onTabComplete(args: Array<String>): MutableList<String> {
        return ArrayList()
    }
}