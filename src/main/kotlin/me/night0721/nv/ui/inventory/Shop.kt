package me.night0721.nv.ui.inventory

import me.night0721.nv.database.ShopDataManager
import me.night0721.nv.entities.items.CustomItemManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player

class Shop : GUIManager() {
    override fun ui(player: Player) {
        init(54, LegacyComponentSerializer.legacyAmpersand().deserialize(title))
        setCloseButton(true)
        setFrame(true, Material.GREEN_STAINED_GLASS_PANE)
        val list = ShopDataManager.items
        val a = intArrayOf(
            10,
            11,
            12,
            13,
            14,
            15,
            16,
            19,
            20,
            21,
            22,
            23,
            24,
            25,
            28,
            29,
            30,
            31,
            32,
            33,
            34,
            37,
            38,
            39,
            40,
            41,
            42,
            43,
            44
        )
        var counter = 0
        for (c in list.keys) {
            if (counter <= 20) {
                val item = CustomItemManager.produceItem(c).clone()
                val itemMeta = item.itemMeta ?: return
                val lore = if (itemMeta.lore() == null) ArrayList<Component>() else itemMeta.lore()!!
                lore.add(
                    Component.text().content("Price (BIN): " + list[c]).color(NamedTextColor.RED).build()
                )
                itemMeta.lore(lore)
                item.itemMeta = itemMeta
                GUI!!.setItem(a[counter], item)
                counter++
            }
        }
        player.openInventory(GUI!!)
    }

    companion object {
        val title = ChatColor.GREEN.toString() + "7-Eleven 24/7"
    }
}