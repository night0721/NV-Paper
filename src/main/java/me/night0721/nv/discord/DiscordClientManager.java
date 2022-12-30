package me.night0721.nv.discord;

import me.night0721.nv.util.Util;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class DiscordClientManager {

  public DiscordClientManager() {
    register();
  }

  public void register() {
    JDABuilder builder = JDABuilder.createDefault(System.getenv("DISCORD_TOKEN"));
    builder.setActivity(Activity.streaming("cath.exe", "https://www.youtube.com/watch?v=YSKDu1gKntY"));
    JDA jda = builder.build();
    Util.info("Discord >> " + jda.getSelfUser().getName() + " is now online!");
  }

}
