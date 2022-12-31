package me.night0721.nv.entities.players

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import org.bukkit.Location

class Techno(location: Location, player: ServerPlayer) :
    Player(player.getLevel(), BlockPos.ZERO, 0.2f, player.getGameProfile(), null) {
    init {
        this.customName = Component.nullToEmpty("Technoblade")
        this.isCustomNameVisible = true
        this.setPos(location.x, location.y, location.z)
    }

    override fun isSpectator(): Boolean {
        return false
    }

    override fun isCreative(): Boolean {
        return false
    }
}