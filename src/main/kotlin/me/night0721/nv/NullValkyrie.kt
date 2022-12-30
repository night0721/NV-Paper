package me.night0721.nv

import me.night0721.nv.commands.CommandManager
import me.night0721.nv.database.DatabaseManager
import me.night0721.nv.database.NPCDataManager
import me.night0721.nv.discord.DiscordClientManager
import me.night0721.nv.entities.miners.CryptoMiner
import me.night0721.nv.events.listeners.CustomEvents
import me.night0721.nv.events.listeners.CustomItemEvents
import me.night0721.nv.events.listeners.DamageEffectEvents
import me.night0721.nv.events.listeners.ServerEvents
import me.night0721.nv.game.tasks.AlwaysDayTask
import me.night0721.nv.ui.inventory.InventoryListener
import me.night0721.nv.ui.player.ScoreboardListener
import me.night0721.nv.util.enchantments.EnchantmentManager
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class NullValkyrie : JavaPlugin() {
    override fun onEnable() {
        EnchantmentManager.register()
        DatabaseManager().connect()
        CommandManager()
        Bukkit.getPluginManager().registerEvents(ServerEvents(), this)
        Bukkit.getPluginManager().registerEvents(InventoryListener(), this)
        Bukkit.getPluginManager().registerEvents(ScoreboardListener(), this)
        Bukkit.getPluginManager().registerEvents(CustomItemEvents(), this)
        Bukkit.getPluginManager().registerEvents(DamageEffectEvents(), this)
        Bukkit.getPluginManager().registerEvents(CustomEvents(), this)
        DiscordClientManager()
        NPCDataManager.reloadNPC()
        CryptoMiner.reloadMiner()
        AlwaysDayTask().runTaskTimer(this, 0, 100)
    }

    companion object {
        fun <T> getPlugin(java: Class<T>): Plugin? {
            return Bukkit.getPluginManager().getPlugin(java.name)
        }
    }
}
// TODO: add comments to functions so docs can be generated
// TODO: Add corpse body when player dies
// TODO: vault to store item
// TODO: withdraw command
// TODO: deposit command
// TODO: add more items using player heads, scraping textures from https://www.freshcoal.com/search.php
// TODO: custom recipes using exactChoice
// TODO: pets using player heads, giving abilities to player
// TODO: market command to show items a player is selling
// TODO: custom model data on block, such as mithril ore to mine
// TODO: skills and abilities system
// TODO: auto generated mobs in dungeons
// TODO: rewards when reached milestones, quests
// TODO: teleportation to different places, to different npcs, hub
// TODO: using pdc to store quests, skills, abilities, etc
// TODO: guilds