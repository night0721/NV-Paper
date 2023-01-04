package me.night0721.nv.game.tasks

import me.night0721.nv.NullValkyrie
import me.night0721.nv.entities.corpses.Body
import me.night0721.nv.entities.corpses.BodyManager
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.scheduler.BukkitRunnable

class BodyRemover : BukkitRunnable() {

    override fun run() {
        val now = System.currentTimeMillis()
        val iterator = BodyManager.bodies.iterator()
        while (iterator.hasNext()) {
            val body: Body = iterator.next()
            if (now - body.whenDied >= 10000) {
                iterator.remove()
                object : BukkitRunnable() {
                    override fun run() {
                        val location: Location = body.npc!!.bukkitEntity.location.clone()
                        Bukkit.getOnlinePlayers().forEach { player: Player ->
                            body.npc!!.setPos(location.x, location.y - 0.01, location.z)
                            (player as CraftPlayer).handle.connection.send(ClientboundTeleportEntityPacket(body.npc!!))
                        }
                        if (!location.add(0.0, 1.0, 0.0).block.isPassable) {
                            BodyManager.deleteNPC(body)
                            this.cancel()
                        }
                    }
                }.runTaskTimerAsynchronously(NullValkyrie.getPlugin(), 0L, 5L)
                val whoDied: Player = Bukkit.getServer().getPlayer(body.whoDied!!)!!
                val inventory: Inventory = whoDied.inventory
                object : BukkitRunnable() {
                    override fun run() {
                        for (item in body.items)
                            inventory.addItem(item).values.forEach{whoDied.world.dropItem(whoDied.location, it)}
                        whoDied.sendMessage("Your dead body has rotten and items have been returned to your inventory.")
                    }
                }.runTaskLater(NullValkyrie.getPlugin(), 0L)

            }
        }
    }
}