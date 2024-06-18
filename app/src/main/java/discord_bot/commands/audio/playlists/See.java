package discord_bot.commands.audio.playlists;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import discord_bot.Main;
import discord_bot.commands.audio.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.dao.Playlist;
import discord_bot.utils.database.PlaylistDatabase;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class See extends Commands {
    
    public See(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {
        
        String name = event.getOption(Main.PLAYLISTS_SEE_OPTION_NAME).getAsString();

        PlaylistDatabase playlistDB = PlaylistDatabase.getInstance();
        Playlist playlist = playlistDB.getPlaylistWithTracks(event.getGuild().getIdLong(), name);
        
        StringBuilder builder = new StringBuilder();

        playlist.getTracks().forEach(track -> builder.append(":arrow_forward: ").append(track.title).append("\n"));

        MessageSender.infoEvent(musicManager.getMessageSender(), builder.toString(), event);
    }
}
