package me.night0721.nv.packets.protocol

import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.Player

class PacketPlayOutEntityMetadata(player: Player?, entity: Entity?, entityData: SynchedEntityData?) : Packet {
    init {
        (player as CraftPlayer?)!!.handle.connection.send(
            ClientboundSetEntityDataPacket(
                entity!!.bukkitEntity.entityId,
                entityData,
                true
            )
        )
    }
}