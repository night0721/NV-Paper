package me.night0721.nv.commands

import me.night0721.nv.entities.pets.ZombiePet
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class BetaCommand : Command("beta", arrayOf<String?>("b"), "Beta", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            val a = ZombiePet(sender.location, sender)
            (sender.world as CraftWorld).handle.addFreshEntity(a)
            val item = ItemStack(Material.NETHERITE_SWORD)
            val itemMeta = item.itemMeta!!
            itemMeta.setCustomModelData(1010101)
            item.itemMeta = itemMeta
            sender.inventory.addItem(item)
            val item2 = ItemStack(Material.GOLDEN_SWORD)
            val itemMeta2 = item2.itemMeta!!
            itemMeta2.setCustomModelData(1010101)
            item2.itemMeta = itemMeta2
            sender.inventory.addItem(item2)
        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<String>): MutableList<String>? {
        return null
    }
}