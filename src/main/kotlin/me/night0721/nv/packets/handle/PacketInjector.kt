package me.night0721.nv.packets.handle

import me.night0721.nv.packets.protocol.Channel
import org.bukkit.entity.Player

class PacketInjector {
    fun addPlayer(p: Player) {
        try {
            val ch = Channel.getChannel(p)
            if (ch!!.pipeline()["PacketInjector"] == null) {
                val h = PacketHandler(p)
                ch.pipeline().addBefore("packet_handler", "PacketInjector", h)
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    fun removePlayer(p: Player) {
        try {
            val ch = Channel.getChannel(p)
            if (ch!!.pipeline()["PacketInjector"] != null) {
                ch.pipeline().remove("PacketInjector")
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }
}