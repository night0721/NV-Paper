package me.night0721.nv.util

import com.google.gson.JsonParser
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import java.io.InputStreamReader
import java.net.URL

object Skin {
    fun getSkin(name: String): Array<String> {
        return try {
            val url = URL("https://api.mojang.com/users/profiles/minecraft/$name")
            val reader = InputStreamReader(url.openStream())
            val uuid = JsonParser.parseReader(reader).asJsonObject["id"].asString
            val url2 = URL("https://sessionserver.mojang.com/session/minecraft/profile/$uuid?unsigned=false")
            val reader2 = InputStreamReader(url2.openStream())
            val properties = JsonParser.parseReader(reader2).asJsonObject["properties"].asJsonArray[0].asJsonObject
            val texture = properties["value"].asString
            val signature = properties["signature"].asString
            arrayOf(texture, signature)
        } catch (e: Exception) {
            arrayOf()
        }
    }

    @JvmStatic
    fun getSkin(player: Player): Array<String> {
        return try {
            val profile = (player as CraftPlayer).profile
            val property = profile.properties["textures"].iterator().next()
            val texture = property.value
            val signature = property.signature
            arrayOf(texture, signature)
        } catch (e: Exception) {
            arrayOf()
        }
    }
}