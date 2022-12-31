package me.night0721.nv.discord

import me.night0721.nv.util.Util.info
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity

class DiscordClientManager {
    init {
        register()
    }

    private fun register() {
        val builder = JDABuilder.createDefault(System.getenv("DISCORD_TOKEN"))
        builder.setActivity(Activity.streaming("cath.exe", "https://www.youtube.com/watch?v=YSKDu1gKntY"))
        val jda = builder.build()
        info("Discord >> " + jda.selfUser.name + " is now online!")
    }
}