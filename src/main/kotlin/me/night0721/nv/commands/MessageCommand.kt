package me.night0721.nv.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil

class MessageCommand : Command("message", arrayOf<String?>("msg"), "Send message to someone", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            if (args.size >= 2) {
                if (Bukkit.getPlayerExact(args[0]) != null) {
                    val target = Bukkit.getPlayerExact(args[0])
                    if (target != sender) {
                        val builder = StringBuilder()
                        for (i in 1 until args.size) {
                            builder.append(args[i]).append(" ")
                        }
                        sender.sendMessage(ChatColor.DARK_AQUA.toString() + "TO " + ChatColor.RED + target!!.name + ChatColor.WHITE + " : " + builder)
                        target.sendMessage(ChatColor.DARK_AQUA.toString() + "FROM " + ChatColor.RED + sender.getName() + ChatColor.WHITE + " : " + builder)
                    } else {
                        sender.sendMessage(ChatColor.RED.toString() + "You cannot send message to yourself")
                    }
                } else {
                    sender.sendMessage(ChatColor.RED.toString() + "You cannot send message to offline players")
                }
            } else {
                sender.sendMessage(ChatColor.RED.toString() + "Invalid parameter, use /msg <Player> <Message>")
            }
        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<String>): MutableList<String> {
        if (args.size == 1) {
            val names: MutableList<String> = ArrayList()
            for (player in Bukkit.getOnlinePlayers()) {
                names.add(player.name)
            }
            return StringUtil.copyPartialMatches(args[0], names, ArrayList())
        }
        return ArrayList()
    }
}