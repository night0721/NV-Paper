package me.night0721.nv.util

import java.util.*
import kotlin.math.roundToLong

class RandomCollection<E> {
    private val map: NavigableMap<Double, E> = TreeMap()
    private val chance = HashMap<E, Double>()
    private val random = Random()
    private var total = 0.0
    fun add(weight: Double, value: E) {
        total += weight
        map[total] = value
        chance[value] = weight
    }

    fun remove(value: E) {
        if (chance.containsKey(value)) {
            chance.remove(value)
            total = 0.0
            map.clear()
            for (e in chance.keys) {
                total += chance[e]!!
                map[total] = e
            }
        }
    }

    fun getRandom(): E? {
        if (total == 0.0) return null
        val value = random.nextDouble() * total
        return map.ceilingEntry(value).value
    }

    fun getChance(v: E): Long {
        var c = 0.0
        for (d in chance.keys) {
            if (d === v) {
                c = chance[d]!!
                break
            }
        }
        return (c / total * 100).roundToLong()
    }

    val all: List<E>
        get() = ArrayList(map.values)
}