package me.night0721.nv.entities.holograms

import me.night0721.nv.packets.protocol.PacketPlayOutEntityMetadata
import me.night0721.nv.packets.protocol.PacketPlayOutSpawnEntity
import net.minecraft.network.chat.Component
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.world.entity.decoration.ArmorStand
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.*

class PerPlayerHologram(player: Player?, lines: Array<String>) {
    init {
        spawnLine(player, lines)
    }

    private fun spawnLine(player: Player?, lines: Array<String>) {
        var c = lines.size * 0.3 - 0.8
        val stands = arrayOfNulls<ArmorStand>(lines.size)
        for (i in lines.indices) {
            val p = (player as CraftPlayer?)!!.handle
            val stand = ArmorStand(p.getLevel(), player!!.location.x, player.location.y + c, player.location.z)
            stand.isInvisible = true
            PacketPlayOutSpawnEntity(player, stand)
            stands[i] = stand
            val watcher = stand.entityData
            watcher.set(
                EntityDataAccessor(2, EntityDataSerializers.OPTIONAL_COMPONENT), Optional.of(
                    Component.nullToEmpty(
                        lines[i]
                    )
                )
            )
            watcher.set(EntityDataAccessor(3, EntityDataSerializers.BOOLEAN), true)
            PacketPlayOutEntityMetadata(player, stand, watcher)
            c -= 0.3
            if (lines.size == i + 1) holograms[stand.bukkitEntity.entityId] = stands
        }
    }

    companion object {
        val holograms = HashMap<Int, Array<ArmorStand?>>()
    }
}