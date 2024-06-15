package discord_bot.commands;

import java.util.List;

import discord_bot.commands.audio.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.UtilsEmbed;
import discord_bot.model.dao.Cache;
import discord_bot.utils.database.Database;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class SeeCache extends Commands {

    public SeeCache(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {

        List<Cache> caches = null;
        
        try { caches = Database.getInstance().getCaches(); } 
        catch (Exception e) {
            e.printStackTrace();
            return;
        }

        MessageEmbed embed = UtilsEmbed.createCacheEmbed(caches);

        event.getHook().sendMessageEmbeds(embed).queue();
    }

    @Override
    public void executeCommands(ButtonInteractionEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
