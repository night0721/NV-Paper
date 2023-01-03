package me.night0721.nv.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.util.StringUtil

class ArmorCommand : Command("armor", arrayOf(), "Give you a set of armor", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            val helmet = ItemStack(Material.LEATHER_HELMET)
            val helmetdata = helmet.itemMeta as LeatherArmorMeta
            helmetdata.displayName(Component.text().content("Angeles Helmet").color(TextColor.fromHexString("#ff23ff")).build())
            helmetdata.setColor(Color.fromRGB(2, 2, 58))
            helmetdata.isUnbreakable = true
            helmet.itemMeta = helmetdata
            sender.inventory.addItem(helmet)
            val cp = ItemStack(Material.LEATHER_CHESTPLATE)
            val cpdata = cp.itemMeta as LeatherArmorMeta
            cpdata.displayName(Component.text().content("Angeles Chestplate").color(TextColor.fromHexString("#ff23ff")).build())
            cpdata.setColor(Color.fromRGB(2, 2, 58))
            cpdata.isUnbreakable = true
            cp.itemMeta = cpdata
            sender.inventory.addItem(cp)
            val leg = ItemStack(Material.LEATHER_LEGGINGS)
            val legdata = leg.itemMeta as LeatherArmorMeta
            legdata.displayName(Component.text().content("Angeles Leggings").color(TextColor.fromHexString("#ff23ff")).build())
            legdata.setColor(Color.fromRGB(2, 2, 58))
            legdata.isUnbreakable = true
            leg.itemMeta = legdata
            sender.inventory.addItem(leg)
            val boot = ItemStack(Material.LEATHER_BOOTS)
            val bootdata = boot.itemMeta as LeatherArmorMeta
            bootdata.displayName(Component.text().content("Angeles Boots").color(TextColor.fromHexString("#ff23ff")).build())
            bootdata.setColor(Color.fromRGB(2, 2, 58))
            bootdata.isUnbreakable = true
            boot.itemMeta = legdata
            sender.inventory.addItem(boot)
        }
    }

    override fun CommandSender?.onTabComplete(args: Array<String>): MutableList<String> {
        if (args.size == 1) {
            return StringUtil.copyPartialMatches(args[0], mutableListOf("angeles", "widow"), ArrayList())
        } else if (args.size == 2) {
            val names: MutableList<String> = ArrayList()
            for (player in Bukkit.getOnlinePlayers()) {
                names.add(player.name)
            }
            return StringUtil.copyPartialMatches(args[1], names, ArrayList())
        }
        return ArrayList()
    }
}