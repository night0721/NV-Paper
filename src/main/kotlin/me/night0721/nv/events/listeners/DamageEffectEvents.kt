package me.night0721.nv.events.listeners

import me.night0721.nv.NullValkyrie
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.entity.Zombie
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.scheduler.BukkitRunnable
import java.text.DecimalFormat
import java.util.function.Consumer

class DamageEffectEvents : Listener {
    val world = Bukkit.getWorld("world")
    val indicators: MutableMap<Entity, Int> = HashMap()
    private val formatter = DecimalFormat("#")
    @EventHandler
    fun onDamage(e: EntityDamageByEntityEvent) {
        val damage = e.finalDamage
        if (e.entity is Zombie) {
            val loc = e.entity.location.clone().add(randomOffset, 1.0, randomOffset)
            assert(world != null)
            world!!.spawn(loc, ArmorStand::class.java) { armorStand: ArmorStand ->
                armorStand.isMarker = true
                armorStand.isVisible = false
                armorStand.setGravity(false)
                armorStand.isSmall = true
                armorStand.isCustomNameVisible = true
                armorStand.customName(
                    Component.text().content(formatter.format(damage).toString()).color(NamedTextColor.RED).decoration(
                        TextDecoration.BOLD, true).build())
                indicators[armorStand] = 30
            }
            removeStands()
        }
    }

    private fun removeStands() {
        object : BukkitRunnable() {
            val stands = indicators.keys
            val removal: MutableList<Entity> = ArrayList()
            override fun run() {
                for (stand in stands) {
                    var ticksLeft = indicators[stand]!!
                    if (ticksLeft == 0) {
                        stand.remove()
                        removal.add(stand)
                        continue
                    }
                    ticksLeft--
                    indicators[stand] = ticksLeft
                }
                removal.forEach(Consumer { o: Entity -> stands.remove(o) })
            }
        }.runTaskTimer(NullValkyrie.getPlugin(NullValkyrie::class.java)!!, 0L, 1L)
    }

    private fun isSpawnable(loc: Location): Boolean {
        val feetBlock = loc.block
        val headBlock = loc.clone().add(0.0, 1.0, 0.0).block
        val upperBlock = loc.clone().add(0.0, 2.0, 0.0).block
        return feetBlock.isPassable && !feetBlock.isLiquid && headBlock.isPassable && !headBlock.isLiquid && upperBlock.isPassable && !upperBlock.isLiquid
    }

    private val randomOffset: Double
        get() {
            var random = Math.random()
            if (Math.random() > 0.5) random *= -1.0
            return random
        }

    private fun getRandomWithNeg(size: Int): Int {
        var random = (Math.random() * (size + 1)).toInt()
        if (Math.random() > 0.5) random *= -1
        return random
    }

    private fun generateRandomCoord(size: Int, world: World?): Location {
        val ranX = getRandomWithNeg(size)
        val ranZ = getRandomWithNeg(size)
        val block = world!!.getHighestBlockAt(ranX, ranZ)
        return block.location
    }

    fun generateRandomCoordIsSpawnable(size: Int): Location {
        while (true) {
            assert(world != null)
            val coord = generateRandomCoord(size, world)
            val spawnable = isSpawnable(coord)
            if (spawnable) {
                return coord
            }
        }
    }
}