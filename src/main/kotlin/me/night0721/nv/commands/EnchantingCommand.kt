package me.night0721.nv.commands

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class EnchantingCommand :
    Command("enchant", arrayOf("et", "enchanting", "enchantingtable"), "Open enchanting table", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        (sender as? Player)?.openEnchanting(sender.location, true)
    }

    override fun CommandSender?.onTabComplete(args: Array<String>): MutableList<String> {
        return ArrayList()
    }
}