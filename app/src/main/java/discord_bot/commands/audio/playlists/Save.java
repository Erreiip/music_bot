package discord_bot.commands.audio.playlists;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.commands.audio.Commands;
import discord_bot.jda.Kawaine;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.MusicEmbded;
import discord_bot.model.TrackScheduler;
import discord_bot.model.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class Save extends Commands {

    public Save(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        if ( ! musicManager.record ) {
            MessageSender.errorEvent(musicManager.getMessageSender(), "No playlist is being recorded.", event);
            return;
        }

        musicManager.record = false;

        try {
            Playlist.writePlaylist(musicManager.playlist);
        } catch (Exception e) {
            MessageSender.errorEvent(musicManager.getMessageSender(), "An error occurred while saving the playlist.", event);
            return;
        }
        
        MessageSender.infoEvent(musicManager.getMessageSender(), "Playlist saved.", event);
    }

    @Override
    public void execute(ButtonInteractionEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
