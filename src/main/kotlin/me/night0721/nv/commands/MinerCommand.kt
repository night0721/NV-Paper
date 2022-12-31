package me.night0721.nv.commands

import me.night0721.nv.database.MinerDataManager
import me.night0721.nv.entities.miners.CryptoMiner
import me.night0721.nv.entities.miners.MinerType
import me.night0721.nv.ui.inventory.Miner
import org.bukkit.*
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import java.util.*

class MinerCommand : Command("miner", arrayOf("miners"), "Miners", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            if (args.isEmpty()) {
                sender.sendMessage(ChatColor.RED.toString() + "Invalid command")
                return
            }
            if (args[0].equals("list", ignoreCase = true)) {
                Miner().UI(sender)
            } else if (args[0].equals("new", ignoreCase = true)) {
                val name = args[2]
                val type: MinerType? = MinerType.getByName(args[1])
                val level = 20
                val rate = 0.4
                val time = System.currentTimeMillis()
                assert(type != null)
                MinerDataManager.setMiner(name, type, level, rate, true, time, sender.location)
                val miner = CryptoMiner(name, type, level, rate, time, sender.location)
                miner.spawn(sender)
            } else if (args[0].equals("claim", ignoreCase = true)) {
                MinerDataManager.setLastClaim(args[1])
                sender.sendMessage(ChatColor.GREEN.toString() + "Claimed")
                val seconds = (Date().time - MinerDataManager.getLastClaim(1)).toInt() / 1000
                CryptoMiner.generate(50, seconds)
            }
        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<String>): MutableList<String> {
        if (args.size == 1) {
            val cc = listOf("new", "claim", "list")
            return StringUtil.copyPartialMatches(args[0], cc, ArrayList())
        } else if (args.size == 2) {
            if (args[0].equals("new", ignoreCase = true)) {
                val choices: MutableList<String?> = ArrayList()
                for (type in MinerType.values()) {
                    choices.add(type.name)
                }
                return StringUtil.copyPartialMatches(args[1], choices, ArrayList())
            } else if (args[0].equals("claim", ignoreCase = true)) {
                val choices: MutableList<String?> = ArrayList()
                for (miner in MinerDataManager.miners.values) {
                    choices.add(miner.name)
                }
                return StringUtil.copyPartialMatches(args[1], choices, ArrayList())
            }
        }
        return ArrayList()
    }
}