package me.night0721.nv.entities.corpses

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import com.mojang.datafixers.util.Pair
import me.night0721.nv.util.Skin
import net.kyori.adventure.text.TextComponent
import net.minecraft.network.protocol.game.*
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.Pose
import net.minecraft.world.item.ItemStack
import net.minecraft.world.scores.PlayerTeam
import net.minecraft.world.scores.Team
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_19_R1.CraftServer
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack
import org.bukkit.craftbukkit.v1_19_R1.scoreboard.CraftScoreboard
import org.bukkit.entity.Player
import java.util.*

class Corpse(p: Player) {
    // create params for class
    init {
        spawn(p)
    }

    private fun spawn(player: Player) {
        val name = (player.name() as TextComponent).content()
        val gameProfile = GameProfile(UUID.randomUUID(), name)
        val skin = Skin.getSkin(name)
        gameProfile.properties.put("textures", Property("textures", skin[0], skin[1]))
        val entityPlayer = ServerPlayer((Bukkit.getServer() as CraftServer).server, (player.world as CraftWorld).handle, gameProfile, null)
        entityPlayer.setPos(player.location.x, player.location.y, player.location.z)
        entityPlayer.pose = Pose.SLEEPING
        val team = PlayerTeam(
            (Bukkit.getScoreboardManager().mainScoreboard as CraftScoreboard).handle,
            (player.name() as TextComponent).content()
        )
        team.nameTagVisibility = Team.Visibility.NEVER
        val playerToAdd = ArrayList<String>()
        playerToAdd.add(name)
        val watcher: SynchedEntityData = entityPlayer.entityData
        watcher.set(EntityDataAccessor(17, EntityDataSerializers.BYTE), 127.toByte())
        val move: ClientboundMoveEntityPacket.Pos = ClientboundMoveEntityPacket.Pos(
            entityPlayer.id, 0, ((player.location.y - 1.7 - player.location.y) * 32).toInt().toShort(), 0, false
        )
        val equipmentList: MutableList<Pair<EquipmentSlot, ItemStack>> = ArrayList()
        equipmentList.add(Pair(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(player.inventory.helmet)))
        equipmentList.add(Pair(EquipmentSlot.CHEST, CraftItemStack.asNMSCopy(player.inventory.chestplate)))
        equipmentList.add(Pair(EquipmentSlot.LEGS, CraftItemStack.asNMSCopy(player.inventory.leggings)))
        equipmentList.add(Pair(EquipmentSlot.FEET, CraftItemStack.asNMSCopy(player.inventory.boots)))
        for (on in Bukkit.getOnlinePlayers()) {
            val p = (on as CraftPlayer).handle.connection
            p.send(ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, entityPlayer))
            p.send(ClientboundAddPlayerPacket(entityPlayer))
            p.send(ClientboundSetPlayerTeamPacket.createRemovePacket(team))
            p.send(ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(team, false))
            p.send(ClientboundSetPlayerTeamPacket.createMultiplePlayerPacket(team, playerToAdd, ClientboundSetPlayerTeamPacket.Action.ADD))
            p.send(ClientboundSetEquipmentPacket(entityPlayer.id, equipmentList))
            p.send(ClientboundSetEntityDataPacket(entityPlayer.id, watcher, false))
            p.send(move)
        }
    }
}