package me.night0721.nv.entities.miners

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import com.mojang.datafixers.util.Pair
import me.night0721.nv.NullValkyrie
import me.night0721.nv.database.MinerDataManager
import me.night0721.nv.game.packets.protocol.PacketPlayOutEntityMetadata
import me.night0721.nv.util.Util.color
import me.night0721.nv.util.Util.info
import me.night0721.nv.util.Util.setFieldValue
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.decoration.ArmorStand
import net.minecraft.world.item.ItemStack
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_19_R1.CraftServer
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import java.util.concurrent.ThreadLocalRandom

open class CryptoMiner(
    var name: String,
    private val minerType: MinerType?,
    var level: Int,
    val rate: Double,
    val lastclaim: Long,
    val location: Location
) {
    private var taskId: Int? = null
    private fun runGenerate() {
        var count = 0
        taskId = object : BukkitRunnable() {
            override fun run() {
                generate()
                count++
                if (count == 24) {
                    count = 0
                    Bukkit.getScheduler().cancelTask(taskId)
                    object : BukkitRunnable() {
                        override fun run() {
                            run {
                                var x = this@CryptoMiner.location.x.toInt() - 2
                                while (x <= this@CryptoMiner.location.x + 2) {
                                    run {
                                        var z = this@CryptoMiner.location.z.toInt() - 2
                                        while (z <= this@CryptoMiner.location.z + 2) {
                                            run {
                                                var y = this@CryptoMiner.location.y.toInt() - 1
                                                while (y <= this@CryptoMiner.location.y - 1) {
                                                    this@CryptoMiner.location.y = 133.0
                                                    if (this@CryptoMiner.location.world.getBlockAt(
                                                            x,
                                                            y,
                                                            z
                                                        ) != this@CryptoMiner.location.clone()
                                                            .subtract(0.0, 1.0, 0.0).block
                                                    ) {
                                                        this@CryptoMiner.location.world.getBlockAt(x, y, z).type =
                                                            getType()
                                                    }
                                                    y++
                                                }
                                            }
                                            z++
                                        }
                                    }
                                    x++
                                }
                            }
                        }
                    }.runTaskLater(NullValkyrie.getPlugin(), 10L)
                    runGenerate()
                }
            }
        }.runTaskTimer(NullValkyrie.getPlugin(), 0, 60).taskId
    }

    fun getType(): Material {
        return minerType!!.material
    }

    fun spawn(player: Player) {
        val stand = ArmorStand((location.world as CraftWorld).handle, location.x + 0.5, location.y, location.z + 0.5)
        stand.isInvulnerable = true
        stand.isNoGravity = true
        val head = org.bukkit.inventory.ItemStack(Material.PLAYER_HEAD, 1)
        val meta = head.itemMeta as SkullMeta
        val profile = GameProfile(UUID.randomUUID(), null)
        // url method: new String(Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", this.getMinerType().getHeadTexture()).getBytes()));
        profile.properties.put("textures", Property("textures", minerType!!.headTexture))
        try {
            setFieldValue(meta, "profile", profile)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        head.itemMeta = meta
        val chest = org.bukkit.inventory.ItemStack(Material.LEATHER_CHESTPLATE)
        val chestdata = chest.itemMeta as LeatherArmorMeta
        chestdata.setColor(Color.fromRGB(2, 2, 58))
        chest.itemMeta = chestdata
        val leg = org.bukkit.inventory.ItemStack(Material.LEATHER_LEGGINGS)
        val legdata = leg.itemMeta as LeatherArmorMeta
        legdata.setColor(Color.fromRGB(2, 2, 58))
        leg.itemMeta = legdata
        val boot = org.bukkit.inventory.ItemStack(Material.LEATHER_BOOTS)
        val bootdata = boot.itemMeta as LeatherArmorMeta
        bootdata.setColor(Color.fromRGB(2, 2, 58))
        boot.itemMeta = bootdata
        val pick = org.bukkit.inventory.ItemStack(Material.GOLDEN_PICKAXE)
        val list: MutableList<Pair<EquipmentSlot, ItemStack>> = ArrayList()
        list.add(Pair(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(head)))
        list.add(Pair(EquipmentSlot.CHEST, CraftItemStack.asNMSCopy(chest)))
        list.add(Pair(EquipmentSlot.LEGS, CraftItemStack.asNMSCopy(leg)))
        list.add(Pair(EquipmentSlot.FEET, CraftItemStack.asNMSCopy(boot)))
        list.add(Pair(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(pick)))
        val gameProfile = GameProfile(UUID.randomUUID(), color(name))
        gameProfile.properties.put("textures", Property("textures", minerType.headTexture))
        val server: MinecraftServer = (Bukkit.getServer() as CraftServer).server
        val w = (player.location.world as CraftWorld).handle
        val m = ServerPlayer(server, w, gameProfile, null)
        val pc = (player as CraftPlayer).handle.connection
        pc.send(ClientboundAddEntityPacket(stand))
        pc.send(ClientboundSetEquipmentPacket(stand.id, list))
        pc.send(ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, m))
        val watcher = stand.entityData
        watcher.set(EntityDataAccessor(0, EntityDataSerializers.BYTE), 20.toByte())
        watcher.set(EntityDataAccessor(5, EntityDataSerializers.BOOLEAN), true)
        watcher.set(EntityDataAccessor(15, EntityDataSerializers.BYTE), 13.toByte())
        PacketPlayOutEntityMetadata(player, stand, watcher)
        runGenerate()
    }

    fun generate() {
        val locs: MutableList<Location> = ArrayList()
        run {
            var x = this.location.x.toInt() - 2
            while (x <= this.location.x + 2) {
                run {
                    var z = this.location.z.toInt() - 2
                    while (z <= this.location.z + 2) {
                        run {
                            var y = this.location.y.toInt() - 1
                            while (y <= this.location.y - 1) {
                                this.location.y = 133.0
                                if (this.location.world.getBlockAt(x, y, z).type == this.getType()) {
                                    locs.add(this.location.world.getBlockAt(x, y, z).location)
                                }
                                y++
                            }
                        }
                        z++
                    }
                }
                x++
            }
        }
        if (locs.size != 0) {
            var closest = locs[0]
            for (location in locs) if (location.distance(this.location) < closest.distance(this.location)) closest =
                location
            val items = ArrayList<org.bukkit.inventory.ItemStack>()
            val random = ThreadLocalRandom.current()
            closest.block.drops.clear()
            val levels = listOf(
                intArrayOf(1, 3),
                intArrayOf(2, 5),
                intArrayOf(3, 7),
                intArrayOf(4, 9),
                intArrayOf(5, 11),
                intArrayOf(6, 13),
                intArrayOf(7, 15),
                intArrayOf(8, 17),
                intArrayOf(9, 19),
                intArrayOf(10, 21),
                intArrayOf(11, 23),
                intArrayOf(12, 25),
                intArrayOf(13, 27),
                intArrayOf(14, 29),
                intArrayOf(15, 31),
                intArrayOf(16, 33),
                intArrayOf(17, 35),
                intArrayOf(18, 37),
                intArrayOf(19, 39),
                intArrayOf(20, 41),
                intArrayOf(21, 43),
                intArrayOf(22, 45),
                intArrayOf(23, 47),
                intArrayOf(24, 49),
                intArrayOf(25, 51),
                intArrayOf(26, 53),
                intArrayOf(27, 55),
                intArrayOf(28, 57),
                intArrayOf(29, 59),
                intArrayOf(30, 61),
                intArrayOf(31, 63),
                intArrayOf(32, 65),
                intArrayOf(33, 67),
                intArrayOf(34, 69),
                intArrayOf(35, 71),
                intArrayOf(36, 73),
                intArrayOf(37, 75),
                intArrayOf(38, 77),
                intArrayOf(39, 79),
                intArrayOf(40, 81),
                intArrayOf(41, 83),
                intArrayOf(42, 85),
                intArrayOf(43, 87),
                intArrayOf(44, 89),
                intArrayOf(45, 91),
                intArrayOf(46, 93),
                intArrayOf(47, 95),
                intArrayOf(48, 97),
                intArrayOf(49, 99),
                intArrayOf(50, 100)
            )
            items.add(
                org.bukkit.inventory.ItemStack(
                    getType(), random.nextInt(levels[level][0], levels[level][1])
                )
            )
            closest.block.type = Material.AIR
            // drop the items
            for (item in items) {
                location.add(0.0, 2.0, 0.0).world.dropItemNaturally(closest, item)
            }
        }
    }

    companion object {
        fun generate(pp: Int, times: Int) {
            var generated = 0
            for (counter in 0 until times) {
                val count = ThreadLocalRandom.current().nextInt(100)
                if (count > pp) generated++
            }
            info(generated.toString())
        }

        fun reloadMiner() {
            for (player in Bukkit.getOnlinePlayers()) for (miner in MinerDataManager.miners.values) {
                miner.spawn(player)
            }
        }

        fun onJoin(player: Player) {
            for (miner in MinerDataManager.miners.values) {
                miner.spawn(player)
            }
        }
    }
}