package me.night0721.nv.commands

import me.night0721.nv.database.CustomWeaponsDataManager
import me.night0721.nv.entities.items.CustomItemManager
import org.bukkit.*
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import java.util.*

class UtilCommand : Command("util", arrayOf(), "Give you a tool", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        val player = sender as Player
        val s = StringBuilder()
        val b = Arrays.asList(*args)
        for (a in args) {
            if (a == b[b.size - 1]) {
                s.append(a)
            } else {
                s.append(a)
                s.append(" ")
            }
        }
        val item = CustomItemManager.produceItem(s.toString())
        if (item!!.hasItemMeta()) {
            player.inventory.addItem(item)
        } else {
            player.sendMessage(ChatColor.RED.toString() + "This item doesn't exist")
        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<String>): List<String> {
        if (args.size == 1) {
            val hh: HashMap<String, Any> = CustomWeaponsDataManager.Companion.getWeapons()
            val cc = ArrayList<String?>()
            for (s in hh.keys) {
                val item = hh[s] as HashMap<String, Any>?
                if (item!!["Type"] == "Util") {
                    cc.add(item["Name"] as String?)
                }
            }
            return StringUtil.copyPartialMatches(args[0], cc, ArrayList())
        }
        return ArrayList()
    }
}