package discord_bot.jda;

import discord_bot.Main;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {

        Main.setCommandsOnGuild(event.getGuild());
    }    

}
