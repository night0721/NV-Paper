package me.night0721.nv.ui.inventory

import me.night0721.nv.entities.items.Items
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class LuckyDraw : GUIManager() {
    override fun ui(player: Player) {
        init(45, title)
        setCloseButton(true)
        setFrame(true, Material.BLUE_STAINED_GLASS_PANE)
        val slots = intArrayOf(10, 12, 14, 16, 28, 30, 32, 34, 19, 25)
        for (s1 in InventoryListener.randomCollection.all) {
            val it: Items? = Items.getByName(s1)
            val item = ItemStack(it!!.material)
            val meta = item.itemMeta ?: return
            meta.displayName(Component.text().content(s1!!).color(NamedTextColor.GREEN).build())
            meta.lore(listOf(
                Component.text().content("").build(),
                Component.text().content("§bChance:" + InventoryListener.randomCollection.getChance(s1) + "%").build(),
                Component.text().content(it.rarity.display).build()
            ))
            item.itemMeta = meta
            GUI!!.setItem(it.slot, item)
        }
        for (s in slots) {
            if (GUI!!.getItem(s) == null) {
                val got = ItemStack(Material.BLACK_STAINED_GLASS_PANE)
                val gotmeta = got.itemMeta
                gotmeta.displayName(
                    Component.text().content("You already got this reward!").color(NamedTextColor.RED)
                        .build()
                )
                got.itemMeta = gotmeta
                GUI!!.setItem(s, got)
            }
        }
        val roll = ItemStack(Material.ARROW)
        val meta = roll.itemMeta ?: return
        meta.lore(listOf(Component.text().content("Pressed to roll!").color(NamedTextColor.YELLOW).build()))
        roll.itemMeta = meta
        GUI!!.setItem(22, roll)
        player.openInventory(GUI!!)
    }

    companion object {
        val title = ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + "X Lucky Draw"
    }
}