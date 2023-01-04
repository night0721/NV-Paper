package me.night0721.nv.events.listeners

import me.night0721.nv.NullValkyrie
import me.night0721.nv.entities.corpses.Corpse
import me.night0721.nv.entities.items.CustomItemManager
import me.night0721.nv.entities.items.Pickaxe
import me.night0721.nv.entities.miners.Rarity
import me.night0721.nv.game.packets.protocol.PacketPlayOutBlockBreakAnimation
import me.night0721.nv.util.Util
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.title.Title
import org.bukkit.*
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftSnowball
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftTippedArrow
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.*
import org.bukkit.event.player.*
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Merchant
import org.bukkit.inventory.MerchantRecipe
import org.bukkit.persistence.PersistentDataType
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class CustomItemEvents : Listener {
    @EventHandler
    fun onEntityDamageByEntity(e: EntityDamageByEntityEvent) {
        if (e.damager.type == EntityType.SNOWBALL) {
            val sb = e.damager as Snowball
            val pl = sb.shooter as Player?
            if (pl!!.inventory.itemInMainHand.itemMeta != null) {
                val name = pl.inventory.itemInMainHand.itemMeta.displayName()
                if (name == Component.text().content("Snow Gun").color(Rarity.ULTRA.color).build()) {
                    (e.damager as Snowball).shooter = pl.player
                    e.damage = 2000.0
                }
            }
        }
    }
    @EventHandler
    fun onPlayerFish(e: PlayerFishEvent) {
        val player = e.player
        if (player.inventory.itemInMainHand.itemMeta != null) {
            val name = player.inventory.itemInMainHand.itemMeta.displayName()
            if (name == Component.text().content("Grappling Hook").color(Rarity.RARE.color).build()) {
                if (e.state == PlayerFishEvent.State.REEL_IN) {
                    val change = e.hook.location.subtract(player.location)
                    player.velocity = change.toVector().multiply(0.4)
                }
            }
        }
    }

    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val player = e.player
        if (player.inventory.itemInMainHand.itemMeta != null) {
            val name = player.inventory.itemInMainHand.itemMeta.displayName()
            if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
                if (name == Component.text().content("Teleport Door").color(Rarity.GRAND.color).build()) {
                    val block = player.getTargetBlock(null, 12)
                    val l = block.location
                    l.add(0.0, 1.0, 0.0)
                    val yaw = player.eyeLocation.yaw
                    val pitch = player.eyeLocation.pitch
                    l.yaw = yaw
                    l.pitch = pitch
                    player.teleport(l)
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 10f, 10f)
                } else if (name == Component.text().content("Snow Gun").color(Rarity.ULTRA.color).build()) {
                    val s = player.launchProjectile(Snowball::class.java, player.location.direction)
                    val namecode = Util.colorOf((name as TextComponent).color()?.asHexString()!!) + name.content()
                    s.velocity = player.location.direction.multiply(10)
                    val weapon = player.inventory.itemInMainHand
                    val weaponMeta = weapon.itemMeta
                    if (weaponMeta != null) {
                        val container = weaponMeta.persistentDataContainer
                        val ammoKey = CustomItemManager.keys["$namecode.ammo"]
                        val ammo = if (container.get(ammoKey!!, PersistentDataType.INTEGER) != null) container.get(
                            ammoKey, PersistentDataType.INTEGER
                        )!! else 0
                        container.set(ammoKey, PersistentDataType.INTEGER, ammo - 1)
                        val max =
                            CustomItemManager.keys["$namecode.max"]?.let { container.get(it, PersistentDataType.INTEGER) }!!
                        weapon.itemMeta = weaponMeta
                        e.player.sendActionBar(
                            LegacyComponentSerializer.legacyAmpersand()
                                .deserialize("&6AK-47 ( " + (ammo - 1) + "/ " + max + " )")
                        )
                    }
                } else if (name == Component.text().content("Terminator").color(Rarity.MYTHIC.color).build()) {
                    val arrow = player.launchProjectile(Arrow::class.java, player.eyeLocation.direction)
                    arrow.velocity = arrow.velocity.multiply(5)
                    arrow.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED
                    arrow.shooter = player
                    arrow.damage = 50.0
                    val a1 = player.launchProjectile(Arrow::class.java, player.eyeLocation.direction)
                    a1.velocity = arrow.velocity.rotateAroundY(Math.toRadians(5.0)).multiply(5)
                    a1.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED
                    a1.shooter = player
                    a1.damage = 50.0
                    val a2 = player.launchProjectile(Arrow::class.java, player.eyeLocation.direction)
                    a2.velocity = arrow.velocity.rotateAroundY(Math.toRadians(-5.0)).multiply(5)
                    a2.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED
                    a2.shooter = player
                    a2.damage = 50.0
                    e.isCancelled = true
                } else if (name == Component.text().content("Explosive Bow").color(Rarity.LEGENDARY.color).build()) {
                    val arrow = player.launchProjectile(Arrow::class.java, player.eyeLocation.direction)
                    arrow.velocity = arrow.velocity.multiply(5)
                    arrow.shooter = player
                    arrow.damage = 50.0
                    e.isCancelled = true
                }
            } else if (e.action == Action.LEFT_CLICK_AIR || e.action == Action.LEFT_CLICK_BLOCK) {
                if (name == Component.text().content("Terminator").color(Rarity.MYTHIC.color).build()) {
                    val arrow = player.launchProjectile(Arrow::class.java, player.eyeLocation.direction)
                    arrow.velocity = arrow.velocity.multiply(5)
                    arrow.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED
                    arrow.shooter = player
                    arrow.damage = 50.0
                    val a1 = player.launchProjectile(Arrow::class.java, player.eyeLocation.direction)
                    a1.velocity = arrow.velocity.rotateAroundY(Math.toRadians(5.0)).multiply(5)
                    a1.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED
                    a1.shooter = player
                    a1.damage = 50.0
                    val a2 = player.launchProjectile(Arrow::class.java, player.eyeLocation.direction)
                    a2.velocity = arrow.velocity.rotateAroundY(Math.toRadians(-5.0)).multiply(5)
                    a2.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED
                    a2.shooter = player
                    a2.damage = 50.0
                    e.isCancelled = true
                } else if (name == Component.text().content("Explosive Bow").color(Rarity.LEGENDARY.color).build()) {
                    val arrow = player.launchProjectile(Arrow::class.java, player.eyeLocation.direction)
                    arrow.velocity = arrow.velocity.multiply(5)
                    arrow.shooter = player
                    arrow.damage = 50.0
                    e.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun onEntityShoot(e: EntityShootBowEvent) {
        if (e.projectile is Arrow) {
            if (e.entity is Player) {
                if ((e.entity as Player).inventory.itemInMainHand.itemMeta != null) {
                    val name = (e.entity as Player).inventory.itemInMainHand.itemMeta.displayName()
                    if (name == Component.text().content("Terminator").color(Rarity.MYTHIC.color).build()) {
                        e.isCancelled = true
                    } else if (name == Component.text().content("Explosive Bow").color(Rarity.LEGENDARY.color).build()) {
                        e.isCancelled = true
                    }
                }
            }
        }
    }

    @EventHandler
    fun onProjectileHit(e: ProjectileHitEvent) {
        if (e.entity.shooter is Player) {
            if ((e.entity.shooter as Player).inventory.itemInMainHand.itemMeta != null) {
                val name = (e.entity.shooter as Player).inventory.itemInMainHand.itemMeta.displayName()
                if (name == Component.text().content("Frag Grenade").color(Rarity.LEGENDARY.color).build()) {
                    if (e.hitBlock == null) {
                        val l = e.hitEntity!!.location
                        e.hitEntity!!.world.createExplosion(l.x, l.y, l.z, 100f, false, false)
                    } else if (e.hitEntity == null) {
                        val l = e.hitBlock!!.location
                        e.hitBlock!!.world.createExplosion(l.x, l.y, l.z, 100f, false, false)
                    }
                } else if (name == Component.text().content("Explosive Bow").color(Rarity.LEGENDARY.color).build()) {
                    val arrow = e.entity as Arrow
                    val al = arrow.location
                    (arrow.shooter as Player).world.createExplosion(al, 100f, false, false)
                }
            }
        }
    }

    @EventHandler
    fun onCreatureSpawn(event: CreatureSpawnEvent) {
        if (event.spawnReason == CreatureSpawnEvent.SpawnReason.EGG) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun projectile(e: ProjectileLaunchEvent) {
        if (e.entity.shooter is Player) {
            if ((e.entity.shooter as Player).inventory.itemInMainHand.itemMeta != null) {
                val name = (e.entity.shooter as Player).inventory.itemInMainHand.itemMeta.displayName()
                if (name != null) {
                    if (name == Component.text().content("Frag Grenade").color(Rarity.LEGENDARY.color).build()
                    ) {
                        val s = e.entity as Egg
                        s.velocity = (e.entity.shooter as Player).location.direction.multiply(10)
                    }

                }
            }
        }
    }

    @EventHandler
    fun onPlayerBucketEmpty(e: PlayerBucketEmptyEvent) {
        val x = e.blockClicked.x + e.blockFace.modX
        val y = e.blockClicked.y + e.blockFace.modY
        val z = e.blockClicked.z + e.blockFace.modZ
        val player = e.player
        if (player.inventory.itemInMainHand.itemMeta != null) {
            val name = player.inventory.itemInMainHand.itemMeta.displayName()
            if (name == Component.text().content("Infinite Water Bucket").color(Rarity.EPIC.color).build()) {
                e.player.world.getBlockAt(x, y, z).type = Material.WATER
                e.isCancelled = true
            } else if (name == Component.text().content("Infinite Lava Bucket").color(Rarity.EPIC.color).build()) {
                e.player.world.getBlockAt(x, y, z).type = Material.LAVA
                e.isCancelled = true
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onDamage(e: EntityDamageByEntityEvent) {
        if (e.entity.type == EntityType.PLAYER) {
            var damager: CraftPlayer? = null
            if (e.damager.type == EntityType.SNOWBALL && (e.damager as CraftSnowball).shooter is CraftPlayer)
                damager = (e.damager as CraftSnowball).shooter as CraftPlayer
            if (e.damager.type == EntityType.ARROW && (e.damager as CraftTippedArrow).shooter is CraftPlayer)
                damager = (e.damager as CraftTippedArrow).shooter as CraftPlayer
            val player = e.entity as Player
            if ((player.health - e.damage) <= 0) {
                e.isCancelled = true
                player.health = 20.0
                Corpse(player)
                val loc = Location(Bukkit.getWorld("world"), 139.5, 133.0, 635.5)
                player.teleport(loc)
                for (p in Bukkit.getOnlinePlayers()) {
                    p.sendMessage(
                        if (damager != null) ChatColor.RED.toString() + player.name + " has been killed by " + damager.name
                        else ChatColor.RED.toString() + player.name + " died"
                    )
                    p.hidePlayer(NullValkyrie.getPlugin(), player)
                }
                object : BukkitRunnable() {
                    override fun run() {
                        for (p in Bukkit.getOnlinePlayers()) {
                            p.showPlayer(NullValkyrie.getPlugin(), player)
                        }
                    }
                }.runTaskLater(NullValkyrie.getPlugin(), 100L)
                countDown(player, intArrayOf(5))
            }
        }
    }

    private var taskID = 0
    private fun countDown(player: Player, a: IntArray) {
        taskID = Bukkit.getServer().scheduler.scheduleSyncRepeatingTask(
            NullValkyrie.getPlugin(
            ), {
                player.showTitle(
                    Title.title(
                        Component.text().append(Component.text().content("YOU DIED!").color(NamedTextColor.RED).build())
                            .build(), Component.text().append(
                            Component.text().content("You will revive in " + a[0] + " seconds")
                                .color(NamedTextColor.GREEN).build()
                        ).build(), Title.Times.times(
                            java.time.Duration.ofSeconds(0),
                            java.time.Duration.ofSeconds(1),
                            java.time.Duration.ofSeconds(0)
                        )
                    )
                )
                a[0]--
                if (a[0] == 0) {
                    Bukkit.getScheduler().cancelTask(taskID)
                }
            }, 0L, 20L
        )
    }

    private val villagerlist: MutableMap<UUID, Merchant> = HashMap()

    @EventHandler
    fun onClick(e: PlayerInteractEntityEvent) {
        val p = e.player
        val clickedEntity = e.rightClicked
        if (clickedEntity is Creeper) {
            if (p.inventory.itemInMainHand.type != Material.STICK) return
            clickedEntity.remove()
            val loc = clickedEntity.getLocation()
            val villager = p.world.spawnEntity(loc, EntityType.VILLAGER) as Villager
            villager.profession = Villager.Profession.TOOLSMITH
            val recipes: MutableList<MerchantRecipe> = ArrayList()
            val bread = MerchantRecipe(ItemStack(Material.BREAD, 3), 10)
            bread.addIngredient(ItemStack(Material.EMERALD, 10))
            recipes.add(bread)
            val tntStick = MerchantRecipe(CustomItemManager.produceItem("Terminator"), 10)
            tntStick.addIngredient(CustomItemManager.produceItem("Widow Sword"))
            recipes.add(tntStick)
            val merchant = Bukkit.createMerchant(Component.text().content("Exchange here").build())
            merchant.recipes = recipes
            villagerlist[villager.uniqueId] = merchant
            p.spawnParticle(Particle.END_ROD, loc, 30, 0.0, 1.0, 0.0, 0.2)
            p.openMerchant(merchant, true)
        }
        if (e.rightClicked is Villager) {
            val merchant = villagerlist[clickedEntity.uniqueId] ?: return
            e.isCancelled = true
            p.openMerchant(merchant, true)
        }
    }

    private val blockStages = HashMap<Location, Int>()
    private val miningCooldown = HashMap<UUID, Long>()

    @EventHandler
    fun onAnimationEvent(e: PlayerAnimationEvent) { //Material blockType, int mineInterval, Pickaxe x
        val player = e.player
        val uuid = player.uniqueId
        if (player.gameMode != GameMode.SURVIVAL) return
        if (miningCooldown.containsKey(uuid) && miningCooldown[uuid]!! > System.currentTimeMillis()) return else miningCooldown.remove(
            uuid
        )
        if (e.animationType != PlayerAnimationType.ARM_SWING) return
        val block = player.getTargetBlockExact(4) ?: return
        val pickaxe = Pickaxe(player.inventory.itemInMainHand)
        val materialsThatCanBeMinedFast =
            pickaxe.multimap[pickaxe.material] // to get all materials that the pickaxe can mine
        if (!materialsThatCanBeMinedFast.contains(block.type)) return
        val miningPerPhase = pickaxe.getMiningPerPhase(block.type)
        miningCooldown[uuid] = System.currentTimeMillis() + miningPerPhase
        var blockStage = blockStages.getOrDefault(block.location, 0)
        blockStage = if (blockStage == 10) 0 else blockStage + 1
        blockStages[block.location] = blockStage
        PacketPlayOutBlockBreakAnimation(player, block.location, blockStages[block.location]!!)
        if (blockStage == 0) {
            blockStages.remove(block.location)
            block.breakNaturally()
        }
    }
}