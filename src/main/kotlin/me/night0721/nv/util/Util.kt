package me.night0721.nv.util

import me.night0721.nv.NullValkyrie
import org.bukkit.ChatColor
import java.util.*

object Util {
    @JvmStatic
    fun centerText(text: String, lineLength: Int): String {
        val builder = StringBuilder()
        val space = ' '
        val distance = (lineLength - text.length) / 2
        val repeat = space.toString().repeat(0.coerceAtLeast(distance))
        builder.append(repeat)
        builder.append(text)
        builder.append(repeat)
        return builder.toString()
    }

    @JvmStatic
    fun color(string: String?): String {
        return ChatColor.translateAlternateColorCodes('&', string!!)
    }

    @JvmStatic
    fun capitalize(str: String?): String? {
        return if (str.isNullOrEmpty()) str else str.substring(0, 1)
            .uppercase(Locale.getDefault()) + str.substring(1)
    }

    @JvmStatic
    @Throws(Exception::class)
    fun getFieldValue(instance: Any, fieldName: String?): Any {
        val field = fieldName?.let { instance.javaClass.getDeclaredField(it) }
        field?.isAccessible = true
        return field?.get(instance) ?: throw Exception("Field not found")
    }

    @JvmStatic
    @Throws(Exception::class)
    fun setFieldValue(instance: Any, fieldName: String?, value: Any?) {
        val field = fieldName?.let { instance.javaClass.getDeclaredField(it) }
        field?.isAccessible = true
        field?.set(instance, value)
    }

    @JvmStatic
    fun info(obj: Any) {
        NullValkyrie.getPlugin().logger.info(color(obj.toString()))
    }

    @JvmStatic
    fun warn(string: String?) {
        NullValkyrie.getPlugin().logger.warning(color(string))
    }
    @JvmStatic
    fun colorOf(string: String) : String {
        val magic = StringBuilder("§x")
        val var3 = string.substring(1).toCharArray()
        val var4 = var3.size
        for (var5 in 0 until var4) {
            val c = var3[var5]
            magic.append('§').append(c)
        }
        return magic.toString()
    }
}