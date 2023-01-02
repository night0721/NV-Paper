package me.night0721.nv.commands

import me.night0721.nv.entities.npcs.NPCManager
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil

class NPCCommand : Command("npc", arrayOf(), "NPCs", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            if (args.isEmpty()) {
                sender.sendMessage(ChatColor.RED.toString() + "Invalid command")
                return
            }
            if (args[0].equals("new", ignoreCase = true)) {
                NPCManager.createNPC(sender, args[1])
            }
        }
    }

    override fun CommandSender?.onTabComplete(args: Array<String>): MutableList<String> {
        if (args.size == 1) {
            val cc = listOf("new", "list")
            return StringUtil.copyPartialMatches(args[0], cc, ArrayList())
        }
        return ArrayList()
    }
}