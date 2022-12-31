package me.night0721.nv.commands

import me.night0721.nv.database.RankDataManager
import me.night0721.nv.util.Rank
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil

class RankCommand : Command("rank", arrayOf(), "Set rank of players", "nv.rank.add") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            if (!sender.isOp()) {
                sender.sendMessage(ChatColor.RED.toString() + "You must be server operator to use this command")
                return
            }
            if (args.size == 2) {
                if (Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
                    val target = Bukkit.getOfflinePlayer(args[0])
                    for (rank in Rank.values()) {
                        if (rank.name.equals(args[1], ignoreCase = true)) {
                            RankDataManager.setRank(target.uniqueId, rank)
                            sender.sendMessage(ChatColor.GREEN.toString() + "You changed " + target.name + "'s rank to " + rank.display)
                            if (target.isOnline) target.player!!.sendMessage(ChatColor.GREEN.toString() + sender.getName() + " set your rank to " + rank.display)
                            return
                        }
                    }
                    sender.sendMessage(ChatColor.RED.toString() + "Invalid Rank, please specify a valid rank, ROOKIE, SPECIAL, ADMIN, OWNER")
                } else sender.sendMessage("This player has never played in this server before!")
            } else sender.sendMessage(ChatColor.RED.toString() + "Invalid parameter, use /rank <Player> <Rank>")
        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<String>): MutableList<String> {
        if (args.size == 1) {
            val names: MutableList<String> = ArrayList()
            for (player in Bukkit.getOnlinePlayers()) {
                names.add(player.name)
            }
            return StringUtil.copyPartialMatches(args[0], names, ArrayList())
        } else if (args.size == 2) {
            val roles: MutableList<String> = ArrayList()
            for (rank in Rank.values()) {
                roles.add(rank.name)
            }
            return StringUtil.copyPartialMatches(args[1], roles, ArrayList())
        }
        return ArrayList()
    }
}