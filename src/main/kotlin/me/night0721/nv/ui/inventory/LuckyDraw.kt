package me.night0721.nv.ui.inventory

import me.night0721.nv.entities.items.Items
import me.night0721.nv.util.RandomCollection.all
import me.night0721.nv.util.RandomCollection.getChance
import me.night0721.nv.util.Util.color
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.List

class LuckyDraw : GUIManager() {
    override fun UI(player: Player) {
        init(45, title)
        setCloseButton(true)
        setFrame(true, Material.BLUE_STAINED_GLASS_PANE)
        val slots = intArrayOf(10, 12, 14, 16, 28, 30, 32, 34, 19, 25)
        for (s1 in InventoryListener.Companion.randomCollection.all) {
            val it: Items = Items.Companion.getByName(s1)
            val item = ItemStack(it.material)
            val meta = item.itemMeta ?: return
            meta.setDisplayName(ChatColor.GREEN.toString() + s1)
            meta.lore = List.of(
                "",
                color("&bChance:" + InventoryListener.Companion.randomCollection.getChance(s1) + "%"),
                it.rarity.display
            )
            item.setItemMeta(meta)
            GUIManager.Companion.GUI!!.setItem(it.slot, item)
        }
        for (s in slots) {
            if (GUIManager.Companion.GUI!!.getItem(s) == null) {
                val got = ItemStack(Material.BLACK_STAINED_GLASS_PANE)
                val gotmeta = got.itemMeta
                gotmeta.setDisplayName(ChatColor.RED.toString() + "You already got this reward!")
                got.setItemMeta(gotmeta)
                GUIManager.Companion.GUI!!.setItem(s, got)
            }
        }
        val roll = ItemStack(Material.ARROW)
        val meta = roll.itemMeta ?: return
        meta.lore = listOf("Press to roll!")
        roll.setItemMeta(meta)
        GUIManager.Companion.GUI!!.setItem(22, roll)
        player.openInventory(GUIManager.Companion.GUI!!)
    }

    companion object {
        val title = ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + "X Lucky Draw"
    }
}