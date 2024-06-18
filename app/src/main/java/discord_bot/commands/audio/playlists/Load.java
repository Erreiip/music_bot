package discord_bot.commands.audio.playlists;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.Main;
import discord_bot.commands.audio.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.dao.Playlist;
import discord_bot.utils.IProcessAudio;
import discord_bot.utils.database.PlaylistDatabase;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Load extends Commands implements IProcessAudio {

    public Load(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {
     
        String name = event.getOption(Main.PLAYLIST_LOAD_OPTION_NAME).getAsString();

        PlaylistDatabase playlistDB = PlaylistDatabase.getInstance();
        Playlist playlist = playlistDB.getPlaylistWithTracks(event.getGuild().getIdLong(), name);

        musicManager.joinChannel(event);

        playlist.getTracks().forEach(track -> musicManager.addSong(event, track.uri, null, this));

        MessageSender.infoEvent(musicManager.getMessageSender(), "Playlist loaded.", event);
    }

    @Override
    public void onTrackGet(SlashCommandInteractionEvent event, AudioTrack track,
            Float speed) {

        musicManager.getScheduler().queue(track, speed != null ? speed : 1, event);
    }
}
