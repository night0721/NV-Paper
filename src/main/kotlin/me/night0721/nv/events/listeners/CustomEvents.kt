package me.night0721.nv.events.listeners

import me.night0721.nv.entities.holograms.PerPlayerHologram
import me.night0721.nv.entities.npcs.NPCManager
import me.night0721.nv.events.custom.InteractHologramEvent
import me.night0721.nv.events.custom.RightClickNPCEvent
import me.night0721.nv.util.Util.color
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket
import net.minecraft.server.level.ServerPlayer
import org.bukkit.ChatColor
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import java.util.function.Consumer

class CustomEvents : Listener {
    @EventHandler
    fun onClick(e: RightClickNPCEvent) {
        val player = e.player
        if (e.npc.bukkitEntity.name.contains("VETTEL")) {
            player.sendMessage(color("Hi"))
        }
    }

    @EventHandler
    fun onClickHologram(e: InteractHologramEvent) {
        if (e.hologram.customName() == null) return
        if (e.hologram.customName() == LegacyComponentSerializer.legacyAmpersand().deserialize(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "CLICK")) {
            e.hologram.getNearbyEntities(0.0, 5.0, 0.0).forEach(Consumer {
                PerPlayerHologram(
                    e.player, arrayOf(
                        ChatColor.RED.toString() + "Player Info:",
                        ChatColor.GOLD.toString() + "Name: " + ChatColor.AQUA + e.player.name,
                        ChatColor.BLUE.toString() + "IP: " + e.player.address
                    )
                )
            })
        }
    }

    @EventHandler
    fun onMove(e: PlayerMoveEvent) {
        NPCManager.getNPCs().values.forEach(Consumer { npc: ServerPlayer? ->
            val location = npc!!.bukkitEntity.location
            location.direction = e.player.location.subtract(location).toVector()
            val yaw = location.yaw
            val pitch = location.pitch
            val con = (e.player as CraftPlayer).handle.connection
            con.send(ClientboundRotateHeadPacket(npc, (yaw % 360 * 256 / 360).toInt().toByte()))
            con.send(
                ClientboundMoveEntityPacket.Rot(
                    npc.bukkitEntity.entityId,
                    (yaw % 360 * 256 / 360).toInt().toByte(),
                    (pitch % 360 * 256 / 360).toInt().toByte(),
                    false
                )
            )
        })
    }
}