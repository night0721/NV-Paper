package me.night0721.nv.entities.npcs

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import com.mojang.datafixers.util.Pair
import me.night0721.nv.NullValkyrie
import me.night0721.nv.database.NPCDataManager
import me.night0721.nv.packets.protocol.PacketPlayOutEntityMetadata
import me.night0721.nv.util.Skin.getSkin
import me.night0721.nv.util.Util.color
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ItemStack
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_19_R1.CraftServer
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack
import org.bukkit.entity.Player
import java.util.*

object NPCManager {
    private val NPCs = HashMap<Int?, ServerPlayer?>()
    fun getNPCs(): HashMap<Int?, ServerPlayer?> {
        return NPCs
    }

    fun createNPC(player: Player, name: String?) { // name must be less than 16 characters including color codes
        val sp = (player as CraftPlayer).handle
        val server = sp.server
        val level = (player.getLocation().world as CraftWorld).handle
        val gameProfile = GameProfile(UUID.randomUUID(), color(name))
        val skin = getSkin(player)
        gameProfile.properties.put("textures", Property("textures", skin[0], skin[1]))
        val npc = ServerPlayer(server, level, gameProfile, null)
        val location = player.getLocation()
        npc.setPos(location.x, location.y, location.z)
        addNPCPacket(npc)
        NPCs[npc.id] = npc
        NPCDataManager.setNPC(
            name,
            player.getLocation().x,
            player.getLocation().y,
            player.getLocation().z,
            player.getLocation().pitch.toInt(),
            player.getLocation().yaw.toInt(),
            player.getLocation().world.name,
            skin[0],
            skin[1]
        )
    }

    private fun addNPCPacket(npc: ServerPlayer) {
        for (player in Bukkit.getOnlinePlayers()) {
            val pc = (player as CraftPlayer).handle.connection
            pc.send(ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, npc))
            pc.send(ClientboundAddPlayerPacket(npc))
            pc.send(ClientboundRotateHeadPacket(npc, (npc.bukkitYaw * 256 / 360).toInt().toByte()))
            val watcher = npc.entityData
            watcher.set(EntityDataAccessor(17, EntityDataSerializers.BYTE), 127.toByte())
            PacketPlayOutEntityMetadata(player, npc, watcher)
            Bukkit.getScheduler().runTaskLaterAsynchronously(
                NullValkyrie.getPlugin(),
                Runnable {
                    pc.send(
                        ClientboundPlayerInfoPacket(
                            ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER,
                            npc
                        )
                    )
                },
                50
            )
            val netheriteAxe = org.bukkit.inventory.ItemStack(Material.NETHERITE_AXE)
            val anotherAxe = org.bukkit.inventory.ItemStack(Material.NETHERITE_INGOT)
            val itemList: MutableList<Pair<EquipmentSlot, ItemStack>> = ArrayList()
            itemList.add(Pair(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(netheriteAxe)))
            itemList.add(Pair(EquipmentSlot.OFFHAND, CraftItemStack.asNMSCopy(anotherAxe)))
            pc.send(ClientboundSetEquipmentPacket(npc.bukkitEntity.entityId, itemList))
        }
    }

    fun addJoinPacket(player: Player) {
        for (npc in NPCs.values) {
            val pc = (player as CraftPlayer).handle.connection
            pc.send(ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, npc))
            npc?.let { ClientboundAddPlayerPacket(it) }?.let { pc.send(it) }
            npc?.let { ClientboundRotateHeadPacket(it, (npc.bukkitYaw * 256 / 360).toInt().toByte()) }
                ?.let { pc.send(it) }
            val watcher = npc!!.entityData
            watcher.set(EntityDataAccessor(17, EntityDataSerializers.BYTE), 127.toByte())
            PacketPlayOutEntityMetadata(player, npc, watcher)
            Bukkit.getScheduler().runTaskLaterAsynchronously(
                NullValkyrie.getPlugin(),
                Runnable {
                    pc.send(
                        ClientboundPlayerInfoPacket(
                            ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER,
                            npc
                        )
                    )
                },
                50
            )
            val netheriteAxe = org.bukkit.inventory.ItemStack(Material.NETHERITE_AXE)
            val anotherAxe = org.bukkit.inventory.ItemStack(Material.NETHERITE_INGOT)
            val itemList: MutableList<Pair<EquipmentSlot, ItemStack>> = ArrayList()
            itemList.add(Pair(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(netheriteAxe)))
            itemList.add(Pair(EquipmentSlot.OFFHAND, CraftItemStack.asNMSCopy(anotherAxe)))
            pc.send(ClientboundSetEquipmentPacket(npc.bukkitEntity.entityId, itemList))
        }
    }

    fun reloadNPC(npcs: List<HashMap<String, Any>>) {
        for (npc in npcs) {
            val location = Location(
                Bukkit.getWorld((npc["world"] as String?)!!),
                npc["x"] as Double,
                npc["y"] as Double,
                npc["z"] as Double,
                (npc["yaw"] as Int).toFloat(),
                (npc["pitch"] as Int).toFloat()
            )
            val gameProfile = GameProfile(UUID.randomUUID(), color(npc["name"] as String?))
            gameProfile.properties.put(
                "textures",
                Property("textures", npc["texture"] as String?, npc["signature"] as String?)
            )
            val server: MinecraftServer = (Bukkit.getServer() as CraftServer).server
            val w = (location.world as CraftWorld).handle
            val ep = ServerPlayer(server, w, gameProfile, null)
            ep.setPos(
                location.x,
                location.y,
                location.z
            ) // NMS: 1.19.2 https://nms.screamingsandals.org/1.19.2/net/minecraft/world/entity/Entity.html absMoveTo
            addNPCPacket(ep)
            NPCs[ep.id] = ep
        }
    }

    fun getNPC(id: Int): ServerPlayer? {
        return NPCs[id]
    }
}