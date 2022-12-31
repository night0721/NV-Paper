package me.night0721.nv.commands

import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import java.util.*

abstract class Command(command: String?, aliases: Array<String?>, description: String?, permission: String?) :
    BukkitCommand(
        command!!
    ) {
    init {
        this.aliases = listOf(*aliases)
        setDescription(description!!)
        this.permission = permission
        try {
            val field = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
            field.isAccessible = true
            val map = field[Bukkit.getServer()] as CommandMap
            map.register(command!!, this)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        onCommand(sender, args)
        return false
    }

    @Throws(IllegalArgumentException::class)
    override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>): MutableList<String> {
        return onTabComplete(sender, args)
    }

    abstract fun onCommand(sender: CommandSender, args: Array<String>)
    abstract fun onTabComplete(sender: CommandSender?, args: Array<String>): MutableList<String>
}