package me.night0721.nv.commands

import me.night0721.nv.entities.pets.ZombiePet
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.entity.Player

class PetCommand : Command("pet", arrayOf(), "Pets", "") {
    override fun onCommand(sender: CommandSender, args: Array<String>) {
        if (sender is Player) {
            val a = ZombiePet(sender.location, sender)
            (sender.world as CraftWorld).handle.addFreshEntity(a)
        }
    }

    override fun CommandSender?.onTabComplete(args: Array<String>): MutableList<String> {
        return ArrayList()
    }
}