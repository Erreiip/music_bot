package discord_bot.commands.audio.playlists;

import discord_bot.commands.audio.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Save extends Commands {

    public Save(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {

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
}
