package me.night0721.nv.commands

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType

class AnvilCommand : Command("anvil", arrayOf<String?>("av"), "Open anvil", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            val av = Bukkit.createInventory(null, InventoryType.ANVIL, "AV")
            sender.openInventory(av)
        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<String>): MutableList<String>? {
        return null
    }
}