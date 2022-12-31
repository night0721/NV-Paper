package me.night0721.nv.entities.pets

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.Goal
import java.util.*

class PathFinderGoalPet(// our pet
    private val pet: Mob, // pet's speed
    private val speed: Double, // distance between owner & pet
    private val distance: Float
) : Goal() {
    private var owner: LivingEntity? = null // owner
    private var x = 0.0 // x
    private var y = 0.0 // y
    private var z = 0.0 // z

    init {
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.TARGET, Flag.JUMP))
    }

    override fun canUse(): Boolean {
        owner = pet.target
        return if (owner == null) false else if (!pet.displayName.string.contains(
                owner!!.name.string
            )
        ) false else if (owner!!.distanceToSqr(pet) > (distance * distance).toDouble()) {
            pet.bukkitEntity.teleport(owner!!.bukkitEntity.location)
            false
        } else {
            x = owner!!.x
            y = owner!!.y
            z = owner!!.z
            true
        }
    }

    override fun start() {
        pet.navigation.moveTo(x, y, z, speed)
    }

    override fun canContinueToUse(): Boolean {
        return !pet.navigation.isDone && owner!!.distanceToSqr(pet) < (distance * distance).toDouble()
    }

    override fun stop() {
        owner = null
    }
}