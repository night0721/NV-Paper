package me.night0721.nv.ui.inventory

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

abstract class GUIManager {
    private var close = false
    abstract fun UI(player: Player)
    fun init(size: Int, title: String?) {
        GUI = Bukkit.createInventory(null, size, title!!)
    }

    fun setCloseButton(boo: Boolean) {
        if (boo) {
            close = true
            val close = ItemStack(Material.BARRIER)
            val closemeta = close.itemMeta
            closemeta?.setDisplayName(ChatColor.WHITE.toString() + "Close the menu")
            close.itemMeta = closemeta
            GUI!!.setItem(0, close)
        } else close = false
    }

    fun setFrame(boo: Boolean, vararg frame: Material?) {
        if (boo) {
            val frames = ItemStack(frame[0]!!)
            when (GUI!!.size) {
                27 -> {
                    if (close) {
                        for (i in intArrayOf(
                            1,
                            2,
                            3,
                            4,
                            5,
                            6,
                            7,
                            8,
                            9,
                            17,
                            18,
                            19,
                            20,
                            21,
                            22,
                            23,
                            24,
                            25,
                            26
                        )) GUI!!.setItem(i, frames)
                    } else {
                        for (i in intArrayOf(
                            0,
                            1,
                            2,
                            3,
                            4,
                            5,
                            6,
                            7,
                            8,
                            9,
                            17,
                            18,
                            19,
                            20,
                            21,
                            22,
                            23,
                            24,
                            25,
                            26
                        )) GUI!!.setItem(i, frames)
                    }
                }

                36 -> {
                    if (close) {
                        for (i in intArrayOf(
                            1,
                            2,
                            3,
                            4,
                            5,
                            6,
                            7,
                            8,
                            9,
                            17,
                            18,
                            26,
                            27,
                            28,
                            29,
                            30,
                            31,
                            32,
                            33,
                            34,
                            35
                        )) GUI!!.setItem(i, frames)
                    } else {
                        for (i in intArrayOf(
                            0,
                            1,
                            2,
                            3,
                            4,
                            5,
                            6,
                            7,
                            8,
                            9,
                            17,
                            18,
                            26,
                            27,
                            28,
                            29,
                            30,
                            31,
                            32,
                            33,
                            34,
                            35
                        )) GUI!!.setItem(i, frames)
                    }
                }

                45 -> {
                    if (close) {
                        for (i in intArrayOf(
                            1,
                            2,
                            3,
                            4,
                            5,
                            6,
                            7,
                            8,
                            9,
                            17,
                            18,
                            26,
                            27,
                            35,
                            36,
                            37,
                            38,
                            39,
                            40,
                            41,
                            42,
                            43,
                            44
                        )) GUI!!.setItem(i, frames)
                    } else {
                        for (i in intArrayOf(
                            0,
                            1,
                            2,
                            3,
                            4,
                            5,
                            6,
                            7,
                            8,
                            9,
                            17,
                            18,
                            26,
                            27,
                            35,
                            36,
                            37,
                            38,
                            39,
                            40,
                            41,
                            42,
                            43,
                            44
                        )) GUI!!.setItem(i, frames)
                    }
                }

                54 -> {
                    if (close) {
                        for (i in intArrayOf(
                            1,
                            2,
                            3,
                            4,
                            5,
                            6,
                            7,
                            8,
                            9,
                            17,
                            18,
                            26,
                            27,
                            35,
                            36,
                            44,
                            45,
                            46,
                            47,
                            48,
                            49,
                            50,
                            51,
                            52,
                            53
                        )) GUI!!.setItem(i, frames)
                    } else {
                        for (i in intArrayOf(
                            0,
                            1,
                            2,
                            3,
                            4,
                            5,
                            6,
                            7,
                            8,
                            9,
                            17,
                            18,
                            26,
                            27,
                            35,
                            36,
                            44,
                            45,
                            46,
                            47,
                            48,
                            49,
                            50,
                            51,
                            52,
                            53
                        )) GUI!!.setItem(i, frames)
                    }
                }
            }
        }
    }

    companion object {
        var GUI: Inventory? = null
    }
}