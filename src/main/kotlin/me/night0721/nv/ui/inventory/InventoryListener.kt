package me.night0721.nv.ui.inventory

import me.night0721.nv.NullValkyrie
import me.night0721.nv.database.UserDataManager
import me.night0721.nv.entities.items.Items
import me.night0721.nv.util.RandomCollection
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class InventoryListener : Listener {
    init {
        randomCollection = RandomCollection()
        for (e in Items.values()) {
            randomCollection.add(e.weight, e.name)
        }
    }

    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        if (e.currentItem == null) return
        if (LegacyComponentSerializer.legacyAmpersand().serialize(e.view.title()) == Menu.title) {
            e.isCancelled = true
            val player = e.whoClicked as Player
            when (e.rawSlot) {
                0 -> {}
                20 -> {
                    player.health = 0.0
                    player.sendMessage(ChatColor.RED.toString() + "又做兵 抵死")
                }

                22 -> player.teleport(player.world.spawnLocation)
                24 -> {
                    player.closeInventory()
                    player.openInventory(GUIManager.GUI!!)
                    return
                }

                else -> return
            }
            player.closeInventory()
        }
        if (LegacyComponentSerializer.legacyAmpersand().serialize(e.view.title()) == Shop.title) {
            e.isCancelled = true
            val player = e.whoClicked as Player
            if (e.rawSlot == 0) {
                player.closeInventory()
            }
        }
        if (LegacyComponentSerializer.legacyAmpersand().serialize(e.view.title()) == LuckyDraw.title) {
            e.isCancelled = true
            val player = e.whoClicked as Player
            if (e.rawSlot == 0) {
                player.closeInventory()
            } else if (e.rawSlot == 22) {
                if (randomCollection.all.isEmpty()) {
                    player.closeInventory()
                    player.sendMessage(ChatColor.RED.toString() + "You already got all the rewards!")
                    return
                }
                UserDataManager().updateUserBank(player.uniqueId.toString(), -100)
                val colors: MutableList<String?> = mutableListOf(
                    "WHITE",
                    "ORANGE",
                    "MAGENTA",
                    "LIGHT_BLUE",
                    "YELLOW",
                    "LIME",
                    "PINK",
                    "GRAY",
                    "LIGHT_GRAY",
                    "CYAN",
                    "PURPLE",
                    "BLUE",
                    "BROWN",
                    "GREEN",
                    "RED",
                    "BLACK"
                )
                val slot1: MutableList<String?> = ArrayList(colors)
                val slot2: MutableList<String?> = ArrayList(colors)
                val slot3: MutableList<String?> = ArrayList(colors)
                val slot4: MutableList<String?> = ArrayList(colors)
                val slot5: MutableList<String?> = ArrayList(colors)
                val slot6: MutableList<String?> = ArrayList(colors)
                val slot7: MutableList<String?> = ArrayList(colors)
                val slot8: MutableList<String?> = ArrayList(colors)
                slot1.shuffle()
                slot2.shuffle()
                slot3.shuffle()
                slot4.shuffle()
                slot5.shuffle()
                slot6.shuffle()
                slot7.shuffle()
                slot8.shuffle()
                val slots = intArrayOf(11, 13, 15, 20, 24, 29, 31, 33)
                object : BukkitRunnable() {
                    var i = 0
                    var ii = 0
                    var time = 0
                    override fun run() {
                        if (colors.size - 1 <= i) i = 0
                        if (ii == 8) ii = 0
                        if (time == 20) {
                            cancel()
                            return
                        }
                        for (slot in slots) {
                            if (slot == 11) {
                                val item = ItemStack(Material.valueOf(slot1[i] + "_STAINED_GLASS_PANE"), 1)
                                GUIManager.GUI!!.setItem(slot, item)
                            }
                            if (slot == 13) {
                                val item = ItemStack(Material.valueOf(slot2[i] + "_STAINED_GLASS_PANE"), 1)
                                GUIManager.GUI!!.setItem(slot, item)
                            }
                            if (slot == 15) {
                                val item = ItemStack(Material.valueOf(slot3[i] + "_STAINED_GLASS_PANE"), 1)
                                GUIManager.GUI!!.setItem(slot, item)
                            }
                            if (slot == 20) {
                                val item = ItemStack(Material.valueOf(slot4[i] + "_STAINED_GLASS_PANE"), 1)
                                GUIManager.GUI!!.setItem(slot, item)
                            }
                            if (slot == 24) {
                                val item = ItemStack(Material.valueOf(slot5[i] + "_STAINED_GLASS_PANE"), 1)
                                GUIManager.GUI!!.setItem(slot, item)
                            }
                            if (slot == 29) {
                                val item = ItemStack(Material.valueOf(slot6[i] + "_STAINED_GLASS_PANE"), 1)
                                GUIManager.GUI!!.setItem(slot, item)
                            }
                            if (slot == 31) {
                                val item = ItemStack(Material.valueOf(slot7[i] + "_STAINED_GLASS_PANE"), 1)
                                GUIManager.GUI!!.setItem(slot, item)
                            }
                            if (slot == 33) {
                                val item = ItemStack(Material.valueOf(slot8[i] + "_STAINED_GLASS_PANE"), 1)
                                GUIManager.GUI!!.setItem(slot, item)
                            }
                        }
                        i++
                        ii++
                        time++
                    }
                }.runTaskTimer(NullValkyrie.getPlugin(), 1L, 5L)
                object : BukkitRunnable() {
                    override fun run() {
                        for (slot in slots) {
                            GUIManager.GUI!!.setItem(slot, ItemStack(Material.AIR))
                        }
                        val s = randomCollection.getRandom()
                        if (s != null) {
                            randomCollection.remove(s)
                            var slot = 0
                            for (it in Items.values()) if (it.name == s) slot = it.slot
                            GUIManager.GUI!!.remove(Items.getByName(s)!!.material)
                            val got = ItemStack(Material.BLACK_STAINED_GLASS_PANE)
                            val gotmeta = got.itemMeta
                            gotmeta.displayName(Component.text().content("You already got this reward!").color(NamedTextColor.RED).build())
                            got.itemMeta = gotmeta
                            GUIManager.GUI!!.setItem(slot, got)
                            for (s1 in randomCollection.all) {
                                val item = ItemStack(Items.getByName(s1)!!.material)
                                val meta = item.itemMeta
                                meta.displayName(Component.text().content(Items.getByName(s1)!!.name).color(NamedTextColor.GREEN).build())
                                val lore = if (meta.lore().isNullOrEmpty()) ArrayList() else meta.lore()!!
                                lore.add(Component.text().content("").build())
                                lore.add(LegacyComponentSerializer.legacySection().deserialize("&bChance: " + randomCollection.getChance(s1) + "%"))
                                lore.add(Items.getByName(s1)!!.rarity.display)
                                meta.lore(lore)
                                item.itemMeta = meta
                                GUIManager.GUI!!.setItem(Items.getByName(s1)!!.slot, item)
                            }
                            val it: Items = Items.getByName(s)!!
                            val item = ItemStack(it.material, 1)
                            val meta = item.itemMeta ?: return
                            meta.displayName(Component.text().content(it.name).color(NamedTextColor.GOLD).build())
                            meta.lore(listOf(it.rarity.display))
                            item.itemMeta = meta
                            player.inventory.addItem(item)
                        } else player.closeInventory()
                    }
                }.runTaskLater(NullValkyrie.getPlugin(), 5L * 20L)
            }
        }
    }

    companion object {
        lateinit var randomCollection: RandomCollection<String?>
    }
}