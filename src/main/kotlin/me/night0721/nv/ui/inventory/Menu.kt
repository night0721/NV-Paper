package me.night0721.nv.ui.inventory

import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

class Menu : GUIManager() {
    override fun UI(player: Player) {
        init(45, title)
        setCloseButton(true)
        setFrame(true, Material.BLUE_STAINED_GLASS_PANE)
        val KYS = ItemStack(Material.WOODEN_SWORD)
        val KYSmeta = KYS.itemMeta ?: return
        KYSmeta.setDisplayName(ChatColor.RED.toString() + "KILL YOURSELF WHEN???")
        KYSmeta.lore = Arrays.asList(
            ChatColor.GRAY.toString() + "KYS",
            ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON"
        )
        KYS.setItemMeta(KYSmeta)
        GUIManager.Companion.GUI!!.setItem(20, KYS)
        val home = ItemStack(Material.MAP)
        val homemeta = home.itemMeta ?: return
        homemeta.setDisplayName(ChatColor.BLUE.toString() + "Teleport to home")
        homemeta.lore = Arrays.asList(
            ChatColor.GRAY.toString() + "Click to teleport back to home",
            ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON"
        )
        home.setItemMeta(homemeta)
        GUIManager.Companion.GUI!!.setItem(22, home)
        val chest = ItemStack(Material.ENDER_CHEST)
        val chestmeta = chest.itemMeta ?: return
        chestmeta.setDisplayName(ChatColor.GREEN.toString() + "Open your chest")
        chestmeta.lore = Arrays.asList(
            ChatColor.GRAY.toString() + "Click to open the chest",
            ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON"
        )
        chest.setItemMeta(chestmeta)
        GUIManager.Companion.GUI!!.setItem(24, chest)
        player.openInventory(GUIManager.Companion.GUI!!)
    }

    companion object {
        val title = ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + "Valkyrie Menu"
    }
}