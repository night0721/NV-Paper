package me.night0721.nv.commands

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TestCommand : Command("test", arrayOf(), "Test", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            if (args.size == 1) {
                if (args[0].equals("hello", ignoreCase = true)) {
                    sender.sendMessage(sender.address.hostString)
                }
            }
        }
    }

    override fun CommandSender?.onTabComplete(args: Array<String>): MutableList<String> {
        return ArrayList()
    }
}