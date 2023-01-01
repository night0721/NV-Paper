package me.night0721.nv.commands

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType

class AnvilCommand : Command("anvil", arrayOf("av"), "Open anvil", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            val av = Bukkit.createInventory(null, InventoryType.ANVIL, Component.text("Anvil"))
            sender.openInventory(av)
        }
    }

    override fun CommandSender?.onTabComplete(args: Array<String>): MutableList<String> {
        return ArrayList()
    }
}