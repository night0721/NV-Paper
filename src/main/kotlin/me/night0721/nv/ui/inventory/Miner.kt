package me.night0721.nv.ui.inventory

import me.night0721.nv.database.MinerDataManager
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.text.SimpleDateFormat
import java.util.*

class Miner : GUIManager() {
    override fun UI(player: Player) {
        init(45, title)
        setCloseButton(true)
        setFrame(true, Material.BLUE_STAINED_GLASS_PANE)
        val a = intArrayOf(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34)
        var counter = 0
        for (c in MinerDataManager.getMiners().values) {
            if (counter <= 20) {
                val item = ItemStack(c.type!!)
                val itemMeta = item.itemMeta
                if (itemMeta != null) {
                    itemMeta.setDisplayName(c.name)
                    val lore: MutableList<String> = ArrayList()
                    lore.add("Level: " + c.level)
                    lore.add("Rate: " + c.rate)
                    lore.add("Last Claim: " + SimpleDateFormat("d MMM yyyy HH:mm:ss").format(Date(c.lastclaim)))
                    itemMeta.lore = lore
                    item.setItemMeta(itemMeta)
                    GUIManager.Companion.GUI!!.setItem(a[counter], item)
                    counter++
                }
            }
            player.openInventory(GUIManager.Companion.GUI!!)
        }
    }

    companion object {
        val title = ChatColor.DARK_AQUA.toString() + "Crypto Miners"
    }
}