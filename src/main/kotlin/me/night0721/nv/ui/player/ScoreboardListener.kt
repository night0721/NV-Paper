package me.night0721.nv.ui.player;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.night0721.nv.database.RankDataManager;
import me.night0721.nv.database.UserDataManager;
import me.night0721.nv.entities.miners.CryptoMiner;
import me.night0721.nv.util.Rank;
import me.night0721.nv.entities.npcs.NPCManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@SuppressWarnings("ConstantConditions")
public class ScoreboardListener implements Listener {

    public final NameTagManager nameTagManager;
    public final SideBarManager sideBarManager;
    private final BelowNameManager belowNameManager;

    public ScoreboardListener() {
        nameTagManager = new NameTagManager();
        sideBarManager = new SideBarManager();
        belowNameManager = new BelowNameManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPlayedBefore()) {
            e.getPlayer().setResourcePack("https://www.dropbox.com/s/7y7p93xzhar6vvw/%C2%A7b%C2%A7lNKRP%201.19.3.zip?dl=1");
            e.getPlayer().showTitle(Title.title(Component.text().append(Component.text().content("Welcome to Vanadium!").color(NamedTextColor.RED).build()).build(), Component.text().append(Component.text().content("Hello!").color(NamedTextColor.GREEN).build()).build()));
            RankDataManager.setRank(player.getUniqueId(), Rank.ROOKIE, this);
            new UserDataManager().createUserBank(e.getPlayer().getUniqueId().toString());
        }
        e.getPlayer().sendPlayerListHeader(Component.text().append(Component.text().content("You are playing on ").color(NamedTextColor.AQUA).build()).append(Component.text().content("127.0.0.1").color(NamedTextColor.GREEN).build()).build());
        e.getPlayer().sendPlayerListFooter(Component.text().append(Component.text().content("Ranks, boosters, & more!").color(NamedTextColor.GOLD).build()).build());
        nameTagManager.setNametags(player);
        nameTagManager.newTag(player);
        sideBarManager.setSideBar(player);
        sideBarManager.start(player);
        belowNameManager.setBelowName(player);
        Rank rank = RankDataManager.getRank(player.getUniqueId());
        e.joinMessage(Component.text()
                .append(Component.text().content(rank.getDisplay() + " ").color(rank.getColor()).build())
                .append(Component.text().content(player.getName()).color(rank.getColor()).build())
                .append(Component.text().content(" joined the server!").color(NamedTextColor.WHITE).build())
                .build()
        );
        if (NPCManager.getNPCs() == null) return;
        if (NPCManager.getNPCs().isEmpty()) return;
        NPCManager.addJoinPacket(e.getPlayer());
        CryptoMiner.onJoin(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.quitMessage(null);
        nameTagManager.removeTag(e.getPlayer());
        e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        AnimatedSideBar board = sideBarManager.board;
        if (board.hasID()) board.stop();
    }

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        e.setCancelled(true);
        Player player = e.getPlayer();
        Rank rank = RankDataManager.getRank(player.getUniqueId());
        Bukkit.broadcast(Component.text()
                .append(Component.text().content(rank.getDisplay() + " ").color(rank.getColor()).build())
                .append(Component.text().content(player.getName()).color(rank.getColor()).build())
                .append(Component.text().content(": ").color(NamedTextColor.WHITE).build())
                .append(Component.text().content(((TextComponent) e.message()).content()).color(NamedTextColor.WHITE).build())
                .build());
    }
}
