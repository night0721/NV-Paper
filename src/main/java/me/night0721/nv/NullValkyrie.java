package me.night0721.nv;

import me.night0721.nv.entities.miners.CryptoMiner;
import me.night0721.nv.events.listeners.*;
import me.night0721.nv.game.tasks.AlwaysDayTask;
import me.night0721.nv.ui.inventory.InventoryListener;
import me.night0721.nv.database.NPCDataManager;
import me.night0721.nv.discord.DiscordClientManager;
import me.night0721.nv.util.enchantments.EnchantmentManager;
import me.night0721.nv.ui.player.ScoreboardListener;
import me.night0721.nv.commands.*;
import me.night0721.nv.database.DatabaseManager;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class NullValkyrie extends JavaPlugin {
    @Override
    public void onEnable() {
        EnchantmentManager.register();
        new DatabaseManager().connect();
        new CommandManager();
        Bukkit.getPluginManager().registerEvents(new ServerEvents(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new ScoreboardListener(), this);
        Bukkit.getPluginManager().registerEvents(new CustomItemEvents(), this);
        Bukkit.getPluginManager().registerEvents(new DamageEffectEvents(), this);
        Bukkit.getPluginManager().registerEvents(new CustomEvents(), this);
        new DiscordClientManager();
        NPCDataManager.reloadNPC();
        CryptoMiner.reloadMiner();
        new AlwaysDayTask().runTaskTimer(this, 0, 100);
    }
}
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
