package discord_bot.commands.playlists;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.Kawaine;
import discord_bot.commands.Commands;
import discord_bot.lava_player.GuildMusicManager;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Save implements Commands {

    @Override
    public void execute(SlashCommandInteractionEvent event, AudioPlayerManager playerManager, Kawaine kawaine) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());

        if ( ! musicManager.record ) {
            event.reply("No playlist is being recorded.").queue();
            return;
        }

        musicManager.record = false;

        try {
            Playlist.writePlaylist(musicManager.playlist);
        } catch (Exception e) {
            event.reply("An error occurred while saving the playlist.").queue();
            return;
        }

        event.reply("Playlist saved.").queue();
    }
    
}
