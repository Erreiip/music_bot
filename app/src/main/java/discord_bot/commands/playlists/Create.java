package discord_bot.commands.playlists;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.Kawaine;
import discord_bot.Main;
import discord_bot.commands.Commands;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Create implements Commands {

    @Override
    public void execute(SlashCommandInteractionEvent event, AudioPlayerManager playerManager, Kawaine kawaine) {
     
        String name = event.getOption(Main.PLAYLIST_CREATE_OPTION_NAME).getAsString();

        try { Playlist.createPlaylist(name); } 
        catch (Exception e) { 
            event.reply("An error occurred while creating the playlist.").queue();
            return;
        }

        event.reply("Playlist created.").queue();
    }
    
}
