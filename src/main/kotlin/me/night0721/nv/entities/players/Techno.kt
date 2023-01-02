package me.night0721.nv.entities.players

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import me.night0721.nv.util.Skin
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import org.bukkit.Location
import java.util.*

class Techno(location: Location, player: ServerPlayer) :


    Player(player.getLevel(), BlockPos.ZERO, 0.2f, player.getGameProfile(), null) {
    init {
        val profile = GameProfile(UUID.randomUUID(), "Technoblade")
        val skin = Skin.getSkin("96m_")
        gameProfile.properties.put("textures", Property("textures", skin[0], skin[1]))
        this.gameProfile = profile
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