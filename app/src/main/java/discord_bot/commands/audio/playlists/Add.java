package discord_bot.commands.audio.playlists;

import java.util.List;
import java.util.concurrent.Semaphore;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.Main;
import discord_bot.commands.Commands;
import discord_bot.database.PlaylistDatabase;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.utils.IProcessAudio;
import discord_bot.utils.SongIdentifier;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

public class Add extends Commands implements IProcessAudio {

    private final Semaphore semaphore;
    
    private String name;

    public Add(GuildMusicManager musicManager) {
        super(musicManager);
        this.semaphore = new Semaphore(1);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {

        semaphore.acquireUninterruptibly();
        
        String name = event.getOption(Main.PLAYLIST_ADD_REMOVE_OPTION_NAME).getAsString();
        String url = event.getOption(Main.PLAYLIST_ADD_OPTION_URL).getAsString();

        String song = SongIdentifier.getSongIdentifier(url);

        this.name = name;

        MessageSender.infoEvent(musicManager.getMessageSender(), "Chargement des sons...", event);

        musicManager.addSongWithoutJoin(event, song, null, this);
    }


    /*
     * IProcessAudio
     * */
    @Override
    public void onTrackGet(IReplyCallback event, AudioTrack track, Float speed) {

        PlaylistDatabase playlistDB = PlaylistDatabase.getInstance();
        playlistDB.addTrackToPlaylist(event.getGuild().getIdLong(), name, track.getInfo().title, track.getInfo().uri);

        MessageSender.infoEvent(musicManager.getMessageSender(), "Sons ajouté à " + name, event);
        
        semaphore.release();
    }

    @Override
    public void onTrackGet(IReplyCallback event, List<AudioTrack> track) {
        
        PlaylistDatabase playlistDB = PlaylistDatabase.getInstance();
        for ( AudioTrack audioTrack : track ) {
            playlistDB.addTrackToPlaylist(event.getGuild().getIdLong(), name, audioTrack.getInfo().title, audioTrack.getInfo().uri);
        }

        MessageSender.infoEvent(musicManager.getMessageSender(), "Sons ajouté à " + name, event);

        semaphore.release();
    }

    @Override
    public void onTrackGet(IReplyCallback event, AudioTrack track, List<AudioTrack> recommendations, Float speed) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onTrackGet'");
    }
}
