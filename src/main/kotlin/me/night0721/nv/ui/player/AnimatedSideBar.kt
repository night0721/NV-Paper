package me.night0721.nv.ui.player

import org.bukkit.Bukkit
import java.util.*

class AnimatedSideBar(private val uuid: UUID) {
    fun setID(id: Int) {
        Tasks[uuid] = id
    }

    fun hasID(): Boolean {
        return Tasks.containsKey(uuid)
    }

    fun stop() {
        Bukkit.getScheduler().cancelTask(Tasks[uuid]!!)
    }

    companion object {
        private val Tasks: MutableMap<UUID, Int> = HashMap()
    }
}