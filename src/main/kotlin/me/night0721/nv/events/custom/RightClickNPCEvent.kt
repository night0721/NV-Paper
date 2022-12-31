package me.night0721.nv.events.custom

import net.minecraft.server.level.ServerPlayer
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class RightClickNPCEvent(val player: Player, val nPC: ServerPlayer) : Event(), Cancellable {
    private var isCancelled = false
    override fun isCancelled(): Boolean {
        return isCancelled
    }

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @get:Suppress("unused")
        val handlerList = HandlerList()
    }
}