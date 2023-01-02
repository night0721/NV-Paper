package me.night0721.nv.ui.player

import org.bukkit.Bukkit
import java.util.*

class AnimatedSideBar(private val uuid: UUID) {

    fun setID(id: Int) {
        tasks[uuid] = id
    }

    fun stop() {
        Bukkit.getScheduler().cancelTask(tasks[uuid]!!)
    }

    companion object {
        private val tasks: MutableMap<UUID, Int> = HashMap()
        fun hasID(animatedSideBar: AnimatedSideBar): Boolean {
            return tasks.containsKey(animatedSideBar.uuid)
        }
    }
}