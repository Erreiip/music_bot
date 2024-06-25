package discord_bot.commands.audio.playlists;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.Main;
import discord_bot.commands.Commands;
import discord_bot.database.PlaylistDatabase;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.dao.Playlist;
import discord_bot.utils.ButtonCustom;
import discord_bot.utils.IProcessAudio;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;

public class Load extends Commands implements IProcessAudio {

    public Load(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {
     
        String name = event.getOption(Main.PLAYLIST_LOAD_OPTION_NAME).getAsString();

        PlaylistDatabase playlistDB = PlaylistDatabase.getInstance();
        Playlist playlist = playlistDB.getPlaylistWithTracks(event.getGuild().getIdLong(), name);

        load(playlist, event);
    }

    @Override
    public void executeCommands(ButtonInteractionEvent event) {

        ButtonCustom button = new ButtonCustom(event.getButton());

        String name = button.getData();

        PlaylistDatabase playlistDB = PlaylistDatabase.getInstance();
        Playlist playlist = playlistDB.getPlaylistWithTracks(event.getGuild().getIdLong(), name);

        load(playlist, event);
    }

    private void load(Playlist playlist, IReplyCallback event) {

        musicManager.joinChannel(event);

        playlist.getTracks().forEach(track -> musicManager.addSong(event, track.uri, null, this));

        MessageSender.infoEvent(musicManager.getMessageSender(), "Playlist loaded.", event);
    }

    @Override
    public void onTrackGet(IReplyCallback event, AudioTrack track,
            Float speed) {

        musicManager.getScheduler().queue(track, speed != null ? speed : 1, event);
    }

    @Override
    public void onTrackGet(IReplyCallback event, List<AudioTrack> track) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onTrackGet'");
    }
}
