package me.night0721.nv.commands

import me.night0721.nv.ui.inventory.Shop
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ShopCommand : Command("7elven", arrayOf<String?>("711", "seven", "7ven"), "Shop", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        Shop().UI(sender as Player)
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<String>): MutableList<String> {
        return ArrayList()
    }
}