package me.night0721.nv.game.packets.protocol

import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.Player

class PacketPlayOutBlockBreakAnimation(player: Player, x: Location, stage: Int) : Packet {
    init {
        val packet = ClientboundBlockDestructionPacket(player.entityId, BlockPos(x.blockX, x.blockY, x.blockZ), stage)
        (player as CraftPlayer).handle.connection.send(packet)
    }
}