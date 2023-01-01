package me.night0721.nv.commands

import me.night0721.nv.ui.inventory.LuckyDraw
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LuckyDrawCommand : Command("luckydraw", arrayOf("ld"), "Generate a lucky draw", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            LuckyDraw().ui(sender)
        }
    }

    override fun CommandSender?.onTabComplete(args: Array<String>): MutableList<String> {
        return ArrayList()
    }
}