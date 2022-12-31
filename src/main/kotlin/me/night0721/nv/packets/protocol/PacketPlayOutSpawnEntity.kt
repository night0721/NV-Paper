package me.night0721.nv.packets.protocol

import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.world.entity.Entity
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.Player

class PacketPlayOutSpawnEntity(player: Player?, entity: Entity?) : Packet {
    init {
        val packet = ClientboundAddEntityPacket(entity)
        (player as CraftPlayer?)!!.handle.connection.send(packet)
    }
}