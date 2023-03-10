package me.night0721.nv.entities.pets

import me.night0721.nv.util.Rarity
import net.kyori.adventure.text.Component
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.goal.FloatGoal
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
import net.minecraft.world.entity.monster.Zombie
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityTargetEvent

class ZombiePet(location: Location, player: Player) : Zombie(EntityType.ZOMBIE, (location.world as CraftWorld).handle) {
    init {
        this.isBaby =
            true // https://nms.screamingsandals.org/1.19.2/net/minecraft/world/entity/monster/Zombie.html setBaby
        this.isInvulnerable = true
        this.setPos(
            location.x,
            location.y,
            location.z
        ) // https://nms.screamingsandals.org/1.19.2/net/minecraft/world/entity/Entity.html setPos
        this.bukkitEntity.customName(
            Component.text().content("${player.name}'s Pet").color(Rarity.GRAND.color).build()
        )
        this.bukkitEntity.isCustomNameVisible = true
        this.setTarget(
            (player as CraftPlayer).handle,
            EntityTargetEvent.TargetReason.CUSTOM,
            true
        ) // https://nms.screamingsandals.org/1.19.2/net/minecraft/world/entity/monster/Zombie.html setGoalTarget
    }

    public override fun registerGoals() {
        goalSelector.addGoal(0, PathFinderGoalPet(this, 1.0, 15f))
        goalSelector.addGoal(1, FloatGoal(this))
        goalSelector.addGoal(2, LookAtPlayerGoal(this, net.minecraft.world.entity.player.Player::class.java, 8.0f))
    }
}