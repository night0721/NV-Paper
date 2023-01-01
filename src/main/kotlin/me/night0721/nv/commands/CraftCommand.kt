package me.night0721.nv.commands

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CraftCommand : Command("craft", arrayOf("ct", "crafting", "craftingtable"), "Open crafting table", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        (sender as? Player)?.openWorkbench(null, true)
    }

    override fun CommandSender?.onTabComplete(args: Array<String>): MutableList<String> {
        return ArrayList()
    }
}