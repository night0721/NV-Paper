package me.night0721.nv.ui.inventory

import me.night0721.nv.NullValkyrie
import me.night0721.nv.database.UserDataManager
import me.night0721.nv.entities.items.Items
import me.night0721.nv.util.RandomCollection
import me.night0721.nv.util.RandomCollection.remove
import me.night0721.nv.util.Util.color
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
            randomCollection.add(e.weight, e.getName())
        }
    }

    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        if (e.currentItem == null) return
        if (e.view.title == Menu.Companion.title) {
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
                    player.openInventory(GUIManager.Companion.GUI!!)
                    return
                }

                else -> return
            }
            player.closeInventory()
        }
        if (e.view.title == Shop.Companion.title) {
            e.isCancelled = true
            val player = e.whoClicked as Player
            if (e.rawSlot == 0) {
                player.closeInventory()
            }
        }
        if (e.view.title == LuckyDraw.Companion.title) {
            e.isCancelled = true
            val player = e.whoClicked as Player
            if (e.rawSlot == 0) {
                player.closeInventory()
            } else if (e.rawSlot == 22) {
                if (randomCollection.all.size == 0) {
                    player.closeInventory()
                    player.sendMessage(ChatColor.RED.toString() + "You already got all the rewards!")
                    return
                }
                UserDataManager().updateUserBank(player.uniqueId.toString(), -100)
                val colors: List<String?> = listOf(
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
                val slot1: List<String?> = ArrayList(colors)
                val slot2: List<String?> = ArrayList(colors)
                val slot3: List<String?> = ArrayList(colors)
                val slot4: List<String?> = ArrayList(colors)
                val slot5: List<String?> = ArrayList(colors)
                val slot6: List<String?> = ArrayList(colors)
                val slot7: List<String?> = ArrayList(colors)
                val slot8: List<String?> = ArrayList(colors)
                Collections.shuffle(slot1)
                Collections.shuffle(slot2)
                Collections.shuffle(slot3)
                Collections.shuffle(slot4)
                Collections.shuffle(slot5)
                Collections.shuffle(slot6)
                Collections.shuffle(slot7)
                Collections.shuffle(slot8)
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
                                GUIManager.Companion.GUI!!.setItem(slot, item)
                            }
                            if (slot == 13) {
                                val item = ItemStack(Material.valueOf(slot2[i] + "_STAINED_GLASS_PANE"), 1)
                                GUIManager.Companion.GUI!!.setItem(slot, item)
                            }
                            if (slot == 15) {
                                val item = ItemStack(Material.valueOf(slot3[i] + "_STAINED_GLASS_PANE"), 1)
                                GUIManager.Companion.GUI!!.setItem(slot, item)
                            }
                            if (slot == 20) {
                                val item = ItemStack(Material.valueOf(slot4[i] + "_STAINED_GLASS_PANE"), 1)
                                GUIManager.Companion.GUI!!.setItem(slot, item)
                            }
                            if (slot == 24) {
                                val item = ItemStack(Material.valueOf(slot5[i] + "_STAINED_GLASS_PANE"), 1)
                                GUIManager.Companion.GUI!!.setItem(slot, item)
                            }
                            if (slot == 29) {
                                val item = ItemStack(Material.valueOf(slot6[i] + "_STAINED_GLASS_PANE"), 1)
                                GUIManager.Companion.GUI!!.setItem(slot, item)
                            }
                            if (slot == 31) {
                                val item = ItemStack(Material.valueOf(slot7[i] + "_STAINED_GLASS_PANE"), 1)
                                GUIManager.Companion.GUI!!.setItem(slot, item)
                            }
                            if (slot == 33) {
                                val item = ItemStack(Material.valueOf(slot8[i] + "_STAINED_GLASS_PANE"), 1)
                                GUIManager.Companion.GUI!!.setItem(slot, item)
                            }
                        }
                        i++
                        ii++
                        time++
                    }
                }.runTaskTimer(NullValkyrie.getPlugin(NullValkyrie::class.java)!!, 1L, 5L)
                object : BukkitRunnable() {
                    override fun run() {
                        for (slot in slots) {
                            GUIManager.Companion.GUI!!.setItem(slot, ItemStack(Material.AIR))
                        }
                        val s = randomCollection.getRandom()
                        if (s != null) {
                            randomCollection.remove(s)
                            var slot = 0
                            for (e in Items.values()) if (e.getName() == s) slot = e.slot
                            GUIManager.Companion.GUI.remove(Items.Companion.getByName(s).getMaterial())
                            val got = ItemStack(Material.BLACK_STAINED_GLASS_PANE)
                            val gotmeta = got.itemMeta
                            gotmeta.setDisplayName(ChatColor.RED.toString() + "You already got this reward!")
                            got.setItemMeta(gotmeta)
                            GUIManager.Companion.GUI!!.setItem(slot, got)
                            for (s1 in randomCollection.all) {
                                val item: ItemStack = ItemStack(Items.Companion.getByName(s1).getMaterial())
                                val meta = item.itemMeta
                                meta.setDisplayName(
                                    ChatColor.GREEN.toString() + Items.Companion.getByName(s1).getName()
                                )
                                val lore = if (meta.lore == null) ArrayList() else meta.lore!!
                                lore.add(0, "")
                                lore.add(1, color("&bChance: " + randomCollection.getChance(s1) + "%"))
                                lore.add(2, Items.Companion.getByName(s1).getRarity().getDisplay())
                                meta.lore = lore
                                item.setItemMeta(meta)
                                GUIManager.Companion.GUI!!.setItem(Items.Companion.getByName(s1).getSlot(), item)
                            }
                            val it: Items = Items.Companion.getByName(s)
                            val item = ItemStack(it.material, 1)
                            val meta = item.itemMeta ?: return
                            meta.setDisplayName(ChatColor.GOLD.toString() + it.getName())
                            meta.lore = java.util.List.of(it.rarity.display)
                            item.setItemMeta(meta)
                            player.inventory.addItem(item)
                        } else player.closeInventory()
                    }
                }.runTaskLater(NullValkyrie.getPlugin(NullValkyrie::class.java)!!, 5L * 20L)
            }
        }
    }

    companion object {
        var randomCollection: RandomCollection<String?>
    }
}