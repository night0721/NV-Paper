package me.night0721.nv.game.tasks

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class AlwaysDayTask : BukkitRunnable() {
    override fun run() {
        val world = Bukkit.getServer().getWorld("world")
        if (world != null) world.time = 0L
    }
}