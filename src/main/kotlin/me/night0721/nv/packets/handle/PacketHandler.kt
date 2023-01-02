package me.night0721.nv.packets.handle

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import me.night0721.nv.NullValkyrie
import me.night0721.nv.entities.holograms.PerPlayerHologram
import me.night0721.nv.entities.npcs.NPCManager
import me.night0721.nv.events.custom.InteractHologramEvent
import me.night0721.nv.events.custom.RightClickNPCEvent
import me.night0721.nv.util.Util.getFieldValue
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.network.protocol.game.ServerboundInteractPacket
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData.DataItem
import net.minecraft.world.entity.decoration.ArmorStand
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.ThreadLocalRandom

class PacketHandler(private val player: Player) : ChannelDuplexHandler() {
    @Throws(Exception::class)
    override fun write(ctx: ChannelHandlerContext, packet: Any, promise: ChannelPromise) {
        if (packet.javaClass.simpleName.equals("PacketPlayOutEntityMetadat", ignoreCase = true)) {
            val pk = packet as ClientboundSetEntityDataPacket
            val entityID = pk.id
            val entity = arrayOf<Entity?>(null)
            // get entity from id
            object : BukkitRunnable() {
                override fun run() {
                    for (e in Bukkit.getWorld("world")!!.entities) {
                        if (e.entityId == entityID && e.type == EntityType.PLAYER) {
                            entity[0] = e
                        }
                    }
                }
            }.runTaskLater(NullValkyrie.getPlugin(), 0)
            if (entity[0] == null) return
            val list = pk.unpackedData
            val value = list!![9] as DataItem<*>
            println(value.accessor)
            val health = ThreadLocalRandom.current().nextInt(5, 20).toFloat()
            list[9] = DataItem(EntityDataAccessor(value.accessor.id, EntityDataSerializers.FLOAT), health)
        }
        super.write(ctx, packet, promise)
    }

    @Throws(Exception::class)
    override fun channelRead(c: ChannelHandlerContext, packet: Any) {
        if (packet.javaClass.simpleName.equals("PacketPlayInUseEntity", ignoreCase = true)) {
            val pk = packet as ServerboundInteractPacket
            val entityID = getFieldValue(packet, "a") as Int
            val sneak = getFieldValue(packet, "c") as Boolean
            Bukkit.getScheduler().scheduleSyncDelayedTask(NullValkyrie.getPlugin(), {
                val stands: Array<ArmorStand?> = PerPlayerHologram.holograms[entityID]
                    ?: return@scheduleSyncDelayedTask
                Bukkit.getPluginManager().callEvent(
                    InteractHologramEvent(
                        player,
                        stands[stands.size - 1]!!.bukkitEntity as org.bukkit.entity.ArmorStand
                    )
                )
                for (i in stands) {
                    (player as CraftPlayer).handle.connection.send(ClientboundRemoveEntitiesPacket(i!!.id))
                }
            }, 0)
            val data = getFieldValue(pk, "b")
            if (data.toString().split("\\$".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[1][0] == 'e') return
            try {
                val hand = getFieldValue(data, "a")
                if (hand.toString() != "MAIN_HAND") return
                val npc = NPCManager.getNPC(entityID) ?: return
                //Right Click
                if (npc.bukkitEntity.entityId == entityID && sneak) Bukkit.getScheduler().scheduleSyncDelayedTask(
                    NullValkyrie.getPlugin(
                    ), { Bukkit.getPluginManager().callEvent(RightClickNPCEvent(player, npc)) }, 0
                )
            } catch (x: NoSuchFieldException) {
                //Left Click
            }
        } else {
            super.channelRead(c, packet)
        }
    }
}