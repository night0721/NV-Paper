package me.night0721.nv.events.listeners

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import me.night0721.nv.NullValkyrie
import me.night0721.nv.entities.corpses.Body
import me.night0721.nv.entities.corpses.BodyManager
import me.night0721.nv.util.Skin
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Pose
import net.minecraft.world.scores.PlayerTeam
import net.minecraft.world.scores.Scoreboard
import net.minecraft.world.scores.Team
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class DeathListener : Listener {
    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        BodyManager.bodies.add(spawnCorpse(e.entity))
        e.drops.clear()
    }

    companion object {
        fun spitItems(whoClicked: Player, body: Body) {
            var y = 0.5
            for (itemStack in body.items) {
                whoClicked.world.dropItem(
                    body.npc!!.bukkitEntity.location.clone().add(-1.0, y, 0.0), itemStack
                )
                y += 0.5
            }
        }
    }

    private fun spawnCorpse(deadPerson: Player): Body {
        val body = Body()
        body.whoDied = deadPerson.uniqueId
        body.items = Arrays.stream(deadPerson.inventory.contents).filter { obj: ItemStack? -> Objects.nonNull(obj) }
            .toArray { size: Int -> arrayOfNulls(size) }
        body.whenDied = System.currentTimeMillis()
        val craftPlayer = deadPerson as CraftPlayer
        val npc = ServerPlayer(
            craftPlayer.handle.getServer()!!,
            craftPlayer.handle.getLevel(),
            GameProfile(UUID.randomUUID(), ChatColor.stripColor(" ")),
            null
        )
        var pl = deadPerson.location.block.location.clone()
        while (pl.block.type == Material.AIR) {
            pl = pl.subtract(0.0, 1.0, 0.0)
        }
        npc.setPos(deadPerson.getLocation().x, pl.y + 1, deadPerson.getLocation().z)
        npc.pose = Pose.SLEEPING
        val armorStand = deadPerson.world.spawnEntity(npc.bukkitEntity.location, EntityType.ARMOR_STAND) as ArmorStand
        armorStand.isSmall = true
        armorStand.isInvulnerable = true
        armorStand.isInvisible = true
        armorStand.setGravity(false)
        val armorStand2 = deadPerson.world.spawnEntity(
            npc.bukkitEntity.location.subtract(1.0, 0.0, 0.0), EntityType.ARMOR_STAND
        ) as ArmorStand
        armorStand2.isSmall = true
        armorStand2.isInvulnerable = true
        armorStand2.isInvisible = true
        armorStand2.setGravity(false)
        val armorStand3 = deadPerson.world.spawnEntity(
            npc.bukkitEntity.location.subtract(2.0, 0.0, 0.0), EntityType.ARMOR_STAND
        ) as ArmorStand
        armorStand3.isSmall = true
        armorStand3.isInvulnerable = true
        armorStand3.isInvisible = true
        armorStand3.setGravity(false)
        val skin = Skin.getSkin(deadPerson)
        npc.gameProfile.properties.put("textures", Property("textures", skin[0], skin[1]))
        val team = PlayerTeam(Scoreboard(), npc.name.string)
        team.nameTagVisibility = Team.Visibility.NEVER
        team.players.add(npc.name.string)
        val watcher: SynchedEntityData = npc.entityData
        watcher.set(EntityDataAccessor(17, EntityDataSerializers.BYTE), 127.toByte())
        Bukkit.getOnlinePlayers().forEach { player: Player ->
            val ps = (player as CraftPlayer).handle.connection
            ps.send(ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, npc))
            ps.send(ClientboundAddPlayerPacket(npc))
            ps.send(ClientboundSetEntityDataPacket(npc.id, watcher, true))
            ps.send(ClientboundSetPlayerTeamPacket.createRemovePacket(team))
            ps.send(ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(team, true))
            object : BukkitRunnable() {
                override fun run() {
                    ps.send(ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, npc))
                }
            }.runTaskLaterAsynchronously(NullValkyrie.getPlugin(), 10L)
        }
        body.npc = npc
        body.armorStands.add(armorStand.entityId)
        body.armorStands.add(armorStand2.entityId)
        body.armorStands.add(armorStand3.entityId)
        return body
    }
}