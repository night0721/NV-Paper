package me.night0721.nv.commands

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ItemCommand : Command("item", arrayOf(), "Items", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
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

            val pris = ItemStack(Material.ITEM_FRAME)
            val prismeta = pris.itemMeta!!
            prismeta.displayName(Component.text().content("Lucky Block").build())
            prismeta.setCustomModelData(7777777)
            pris.itemMeta = prismeta
            sender.inventory.addItem(pris)

            val pris2 = ItemStack(Material.ITEM_FRAME)
            val prismeta2 = pris2.itemMeta!!
            prismeta2.displayName(Component.text().content("Ruby Ore").build())
            prismeta2.setCustomModelData(7777778)
            pris2.itemMeta = prismeta2
            sender.inventory.addItem(pris2)

            val pris3 = ItemStack(Material.ITEM_FRAME)
            val prismeta3 = pris3.itemMeta!!
            prismeta3.displayName(Component.text().content("Ruby Block").build())
            prismeta3.setCustomModelData(7777779)
            pris3.itemMeta = prismeta3
            sender.inventory.addItem(pris3)

            val cmd = ItemStack(Material.COMMAND_BLOCK)
            val cmdmeta = cmd.itemMeta!!
            cmdmeta.displayName(Component.text().content("Ruby").build())
            cmdmeta.setCustomModelData(7777777)
            cmd.itemMeta = cmdmeta
            sender.inventory.addItem(cmd)
        }
    }

    override fun CommandSender?.onTabComplete(args: Array<String>): MutableList<String> {
        return ArrayList()
    }
}