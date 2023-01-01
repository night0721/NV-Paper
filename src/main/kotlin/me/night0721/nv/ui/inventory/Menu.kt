package me.night0721.nv.ui.inventory

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Menu : GUIManager() {
    override fun ui(player: Player) {
        init(45, LegacyComponentSerializer.legacyAmpersand().deserialize(title))
        setCloseButton(true)
        setFrame(true, Material.BLUE_STAINED_GLASS_PANE)
        val kys = ItemStack(Material.WOODEN_SWORD)
        val kysmeta = kys.itemMeta ?: return
        kysmeta.displayName(Component.text().content("KYS WHEN?").color(NamedTextColor.RED).build())
        kysmeta.lore(
            listOf(
                Component.text().content("KYS").color(NamedTextColor.GRAY).build(),
                Component.text().content("COMMON").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true)
                    .build()
            )
        )
        kys.itemMeta = kysmeta
        GUI!!.setItem(20, kys)
        val home = ItemStack(Material.MAP)
        val homemeta = home.itemMeta ?: return
        homemeta.displayName(Component.text().content("Teleport to home").color(NamedTextColor.BLUE).build())
        homemeta.lore(listOf(
            Component.text().content("Click to teleport back to home").color(NamedTextColor.GRAY).build(),
            Component.text().content("COMMON").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true)
                .build()
        ))
        home.itemMeta = homemeta
        GUI!!.setItem(22, home)
        val chest = ItemStack(Material.ENDER_CHEST)
        val chestmeta = chest.itemMeta ?: return
        chestmeta.displayName(Component.text().content("Open your chest").color(NamedTextColor.GREEN).build())
        chestmeta.lore(listOf(
            Component.text().content("Click to open the chest").color(NamedTextColor.GRAY).build(),
            Component.text().content("COMMON").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true)
                .build()
        ))
        chest.itemMeta = chestmeta
        GUI!!.setItem(24, chest)
        player.openInventory(GUI!!)
    }

    companion object {
        val title = ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + "Valkyrie Menu"
    }
}