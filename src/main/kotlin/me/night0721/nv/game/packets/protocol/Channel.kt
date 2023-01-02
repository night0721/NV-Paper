package me.night0721.nv.game.packets.protocol

import io.netty.channel.Channel
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.Player

object Channel {
    fun getChannel(player: Player): Channel {
        return (player as CraftPlayer).handle.connection.getConnection().channel // NMS: 1.19.2 https://nms.screamingsandals.org/1.19.2/net/minecraft/server/network/ServerGamePacketListenerImpl.html PlayerConnection -> NetworkManager -> Channel
    }
}