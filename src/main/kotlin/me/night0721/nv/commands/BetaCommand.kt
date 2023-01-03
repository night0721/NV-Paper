package me.night0721.nv.commands

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BetaCommand : Command("beta", arrayOf("b"), "Beta", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            println("Alpha")
        }
    }

    override fun CommandSender?.onTabComplete(args: Array<String>): MutableList<String> {
        return ArrayList()
    }
}