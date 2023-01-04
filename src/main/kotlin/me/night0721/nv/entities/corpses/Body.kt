package me.night0721.nv.entities.corpses

import net.minecraft.server.level.ServerPlayer
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.ArrayList

class Body {
    var whoDied: UUID? = null
    var npc: ServerPlayer? = null
    lateinit var items: Array<ItemStack>
    var armorStands: MutableList<Int> = ArrayList()
    var whenDied: Long = 0
}