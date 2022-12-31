package me.night0721.nv.entities.players;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Location;

public class Techno extends Player {
    public Techno(Location location, ServerPlayer player) {
        super(player.getLevel(), BlockPos.ZERO, 0.2F, player.getGameProfile(), null);
        this.setCustomName(Component.nullToEmpty("Technoblade"));
        this.setCustomNameVisible(true);
        this.setPos(location.getX(), location.getY(), location.getZ());

    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
