package me.night0721.nv.commands

import me.night0721.nv.database.CustomWeaponsDataManager
import me.night0721.nv.entities.items.CustomItemManager
import org.bukkit.*
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import java.util.*

class WeaponCommand : Command("weapon", arrayOf(), "Give you a weapon", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        val player = sender as Player
        val builder = StringBuilder()
        if (args.size == 0) {
            player.sendMessage(ChatColor.RED.toString() + "This item doesn't exist")
        } else {
            val arglist = Arrays.asList(*args)
            for (arg in args) {
                if (arg == arglist[arglist.size - 1]) {
                    builder.append(arg)
                } else {
                    builder.append(arg)
                    builder.append(" ")
                }
            }
            val item = CustomItemManager.produceItem(builder.toString())
            if (item!!.hasItemMeta()) {
                player.inventory.addItem(item)
            } else {
                player.sendMessage(ChatColor.RED.toString() + "This item doesn't exist")
            }
        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<String>): MutableList<String> {
        if (args.size == 1) {
            val hh: HashMap<String, Any> = CustomWeaponsDataManager.weapons
            val cc = ArrayList<String?>()
            for (s in hh.keys) {
                val item = hh[s] as HashMap<*, *>?
                if (item!!["Type"] == "Weapon") {
                    cc.add(item["Name"] as String?)
                }
            }
            return StringUtil.copyPartialMatches(args[0], cc, ArrayList())
        }
        return ArrayList()
    }
}