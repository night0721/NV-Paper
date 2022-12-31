package me.night0721.nv.events.listeners

import me.night0721.nv.NullValkyrie
import me.night0721.nv.entities.items.CustomItemManager
import me.night0721.nv.entities.items.Pickaxe
import me.night0721.nv.entities.miners.Rarity
import me.night0721.nv.packets.protocol.PacketPlayOutBlockBreakAnimation
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.*
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.*
import org.bukkit.event.player.*
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Merchant
import org.bukkit.inventory.MerchantRecipe
import org.bukkit.persistence.PersistentDataType
import java.util.*

class CustomItemEvents : Listener {
    @EventHandler
    fun onEntityDamageByEntity(e: EntityDamageByEntityEvent) {
        if (e.damager.type == EntityType.SNOWBALL) {
            val sb = e.damager as Snowball
            val pl = sb.shooter as Player?
            if (pl!!.inventory.itemInMainHand.itemMeta != null) {
                val name = pl.inventory.itemInMainHand.itemMeta.displayName
                if (name.equals(Rarity.ULTRA.color + "Snow Gun", ignoreCase = true)) {
                    (e.damager as Snowball).shooter = pl.player
                    e.damage = 2000.0
                } else if (name.equals("AA-12", ignoreCase = true)) {
                    e.damage = 7.0
                } else {
                    e.damage = 0.0
                }
            }
        }
    }

    @EventHandler
    fun onPlayerFish(e: PlayerFishEvent) {
        val player = e.player
        if (player.inventory.itemInMainHand.itemMeta != null) {
            val name = player.inventory.itemInMainHand.itemMeta.displayName
            if (name.equals(Rarity.RARE.color + "Grappling Hook", ignoreCase = true)) {
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
            val name = player.inventory.itemInMainHand.itemMeta.displayName
            if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
                if (name.equals(Rarity.GRAND.color + "Teleport Door", ignoreCase = true)) {
                    val block = player.getTargetBlock(null, 12)
                    val l = block.location
                    l.add(0.0, 1.0, 0.0)
                    val yaw = player.eyeLocation.yaw
                    val pitch = player.eyeLocation.pitch
                    l.yaw = yaw
                    l.pitch = pitch
                    player.teleport(l)
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 10f, 10f)
                } else if (name.equals(Rarity.ULTRA.color + "Snow Gun", ignoreCase = true)) {
                    val s = player.launchProjectile(Snowball::class.java, player.location.direction)
                    s.velocity = player.location.direction.multiply(10)
                    val weapon = player.inventory.itemInMainHand
                    val weaponMeta = weapon.itemMeta
                    if (weaponMeta != null) {
                        val container = weaponMeta.persistentDataContainer
                        val ammoKey = CustomItemManager.keys["$name.ammo"]
                        val ammo = if (container.get(ammoKey!!, PersistentDataType.INTEGER) != null) container.get(
                            ammoKey, PersistentDataType.INTEGER
                        )!! else 0
                        container.set(ammoKey, PersistentDataType.INTEGER, ammo - 1)
                        val max = container.get(CustomItemManager.keys["$name.max"], PersistentDataType.INTEGER)!!
                        weapon.setItemMeta(weaponMeta)
                        e.player.spigot().sendMessage(
                            ChatMessageType.ACTION_BAR, *TextComponent.fromLegacyText(
                                ChatColor.translateAlternateColorCodes(
                                    '&',
                                    "&6AK-47 ( " + (ammo - 1) + "/ " + max + " )"
                                )
                            )
                        )
                    }
                } else if (name.equals(Rarity.MYTHIC.color + "Terminator", ignoreCase = true)) {
                    val arrow = player.launchProjectile(Arrow::class.java, player.eyeLocation.direction)
                    arrow.velocity = arrow.velocity.multiply(5)
                    arrow.pickupStatus = Arrow.PickupStatus.DISALLOWED
                    arrow.shooter = player
                    arrow.damage = 50.0
                    val a1 = player.launchProjectile(Arrow::class.java, player.eyeLocation.direction)
                    a1.velocity = arrow.velocity.rotateAroundY(Math.toRadians(5.0)).multiply(5)
                    a1.pickupStatus = Arrow.PickupStatus.DISALLOWED
                    a1.shooter = player
                    a1.damage = 50.0
                    val a2 = player.launchProjectile(Arrow::class.java, player.eyeLocation.direction)
                    a2.velocity = arrow.velocity.rotateAroundY(Math.toRadians(-5.0)).multiply(5)
                    a2.pickupStatus = Arrow.PickupStatus.DISALLOWED
                    a2.shooter = player
                    a2.damage = 50.0
                    e.isCancelled = true
                } else if (name.equals(Rarity.LEGENDARY.color + "Explosive Bow", ignoreCase = true)) {
                    val arrow = player.launchProjectile(Arrow::class.java, player.eyeLocation.direction)
                    arrow.velocity = arrow.velocity.multiply(5)
                    arrow.shooter = player
                    arrow.damage = 50.0
                    e.isCancelled = true
                }
            } else if (e.action == Action.LEFT_CLICK_AIR || e.action == Action.LEFT_CLICK_BLOCK) {
                if (name.equals(Rarity.MYTHIC.color + "Terminator", ignoreCase = true)) {
                    val arrow = player.launchProjectile(Arrow::class.java, player.eyeLocation.direction)
                    arrow.velocity = arrow.velocity.multiply(5)
                    arrow.pickupStatus = Arrow.PickupStatus.DISALLOWED
                    arrow.shooter = player
                    arrow.damage = 50.0
                    val a1 = player.launchProjectile(Arrow::class.java, player.eyeLocation.direction)
                    a1.velocity = arrow.velocity.rotateAroundY(Math.toRadians(5.0)).multiply(5)
                    a1.pickupStatus = Arrow.PickupStatus.DISALLOWED
                    a1.shooter = player
                    a1.damage = 50.0
                    val a2 = player.launchProjectile(Arrow::class.java, player.eyeLocation.direction)
                    a2.velocity = arrow.velocity.rotateAroundY(Math.toRadians(-5.0)).multiply(5)
                    a2.pickupStatus = Arrow.PickupStatus.DISALLOWED
                    a2.shooter = player
                    a2.damage = 50.0
                    e.isCancelled = true
                } else if (name.equals(Rarity.LEGENDARY.color + "Explosive Bow", ignoreCase = true)) {
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
                if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
                    val name: String = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
                    if (name.equals(Rarity.MYTHIC.color + "Terminator", ignoreCase = true)) {
                        e.isCancelled = true
                    } else if (name.equals(Rarity.LEGENDARY.color + "Explosive Bow", ignoreCase = true)) {
                        e.isCancelled = true
                    }
                }
            }
        }
    }

    @EventHandler
    fun onProjectileHit(e: ProjectileHitEvent) {
        if (e.entity.shooter is Player) {
            if (shooter.getInventory().getItemInMainHand().getItemMeta() != null) {
                val name: String = shooter.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
                if (name.equals(Rarity.LEGENDARY.color + "Frag Grenade", ignoreCase = true)) {
                    if (e.hitBlock == null) {
                        val l = e.hitEntity!!.location
                        e.entity.shooter = shooter
                        e.hitEntity!!.world.createExplosion(l.x, l.y, l.z, 100f, false, false)
                    } else if (e.hitEntity == null) {
                        val l = e.hitBlock!!.location
                        e.hitBlock!!.world.createExplosion(l.x, l.y, l.z, 100f, false, false)
                    }
                } else if (name.equals(Rarity.LEGENDARY.color + "Explosive Bow", ignoreCase = true)) {
                    val arrow = e.entity as Arrow
                    val al = arrow.location
                    arrow.shooter = shooter
                    shooter.getWorld().createExplosion(al, 100f, false, false)
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
    fun Projectile(e: ProjectileLaunchEvent) {
        if (e.entity.shooter is Player) {
            if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
                val name: String = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
                if (name.equals(Rarity.LEGENDARY.color + "Frag Grenade", ignoreCase = true)) {
                    val s = e.entity as Egg
                    s.velocity = player.getLocation().getDirection().multiply(10)
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
            val name = player.inventory.itemInMainHand.itemMeta.displayName
            if (name.equals(Rarity.EPIC.color + "Infinite Water Bucket", ignoreCase = true)) {
                e.player.world.getBlockAt(x, y, z).type = Material.WATER
                e.isCancelled = true
            } else if (name.equals(Rarity.EPIC.color + "Infinite Lava Bucket", ignoreCase = true)) {
                e.player.world.getBlockAt(x, y, z).type = Material.LAVA
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onDamage(e: EntityDamageByEntityEvent) {
        if (e.entity is Player) {
            //            if ((player.getHealth() - e.getDamage()) <= 0) {
//                e.setCancelled(true);
//                Location loc = player.getWorld().getBlockAt(-3, 23, -3).getLocation();
//                player.teleport(loc);
//                for (Player p : Bukkit.getOnlinePlayers()) {
//                    p.sendMessage(e.getDamager() instanceof Player
//                            ? ChatColor.RED + player.getName() + " has been killed by " + e.getDamager().getName()
//                            : ChatColor.RED + player.getName() + " died");
//                    p.hidePlayer(player);
//                }
//                new BukkitRunnable() {
//                    @Override
//                    public void run() {
//                        for (Player p : Bukkit.getOnlinePlayers()) {
//                            p.showPlayer(player);
//                        }
//                        player.setHealth(20);
//                        player.teleport(generateRandomCoord(9, Bukkit.getWorld("world")));
//                    }
//                }.runTaskLater(NullValkyrie.getPlugin(NullValkyrie.class), 100L);
//                countDown(player, new int[]{5});
//            }
        }
    }

    private var taskID = 0
    fun countDown(player: Player, a: IntArray) {
        taskID = Bukkit.getServer().scheduler.scheduleSyncRepeatingTask(
            NullValkyrie.getPlugin(
                NullValkyrie::class.java
            )!!, {
                player.sendTitle(
                    ChatColor.RED.toString() + "YOU DIED!",
                    ChatColor.GREEN.toString() + "You will revive in " + a[0] + " seconds",
                    0,
                    20,
                    0
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
            val merchant = Bukkit.createMerchant("Exchange here")
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