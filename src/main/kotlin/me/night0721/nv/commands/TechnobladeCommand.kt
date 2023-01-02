package me.night0721.nv.commands

import me.night0721.nv.entities.players.Techno
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.Player

class TechnobladeCommand : Command("techno", arrayOf("technoblade"), "Spawns technoblade", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            Techno(sender.location, (sender as CraftPlayer).handle)
        }
    }

    override fun CommandSender?.onTabComplete(args: Array<String>): MutableList<String> {
        return ArrayList()
    }
}