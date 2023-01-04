package me.night0721.nv.entities.corpses

import me.night0721.nv.NullValkyrie
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

object BodyManager {
    val bodies: MutableList<Body> = ArrayList()
    fun deleteNPC(body: Body) {
        Bukkit.getOnlinePlayers().forEach { player: Player? ->
            val ps = (player as CraftPlayer).handle.connection
            ps.send(ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, body.npc))
            ps.send(ClientboundRemoveEntitiesPacket(body.npc!!.id))
        }
        object: BukkitRunnable() {
            override fun run() {
                for (armorStand in body.armorStands) {
                    for (entity in Bukkit.getWorld("world")!!.entities) {
                        if (entity.entityId == armorStand)
                            entity.remove()
                    }
                }
            }
        }.runTaskLater(NullValkyrie.getPlugin(), 1L)

    }

}