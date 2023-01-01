package me.night0721.nv.commands

import me.night0721.nv.ui.inventory.Shop
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ShopCommand : Command("7elven", arrayOf("711", "seven", "7ven"), "Shop", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        Shop().ui(sender as Player)
    }

    override fun CommandSender?.onTabComplete(args: Array<String>): MutableList<String> {
        return ArrayList()
    }
}