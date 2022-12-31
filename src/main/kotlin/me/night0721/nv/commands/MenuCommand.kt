package me.night0721.nv.commands

import me.night0721.nv.ui.inventory.Menu
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MenuCommand : Command("menu", arrayOf<String?>("m"), "Open the menu", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            Menu().UI(sender)
        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<String>): MutableList<String>? {
        return null
    }
}