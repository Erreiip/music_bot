package discord_bot.commands.audio.playlists;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.Main;
import discord_bot.commands.audio.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.utils.IProcessAudio;
import discord_bot.utils.SongIdentifier;
import discord_bot.utils.database.PlaylistDatabase;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Add extends Commands implements IProcessAudio {

    private final Lock lock = new ReentrantLock();
    
    private int loadingSongs;
    private String name;

    public Add(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {

        lock.lock();
        
        String name = event.getOption(Main.PLAYLIST_ADD_REMOVE_OPTION_NAME).getAsString();
        String url = event.getOption(Main.PLAYLIST_ADD_OPTION_URL).getAsString();

        List<String> songs = new ArrayList<>();

        if ( SongIdentifier.isPlaylist(url) ) {
            songs = SongIdentifier.getPlaylist(url);           
        } else {
            songs.add(SongIdentifier.getSongIdentifier(url));
        }

        this.loadingSongs = songs.size();
        this.name = name;

        for ( String song : songs ) {
            musicManager.addSongWithoutJoin(event, song, null, this);
        }
    }

    @Override
    public void onTrackGet(SlashCommandInteractionEvent event, AudioTrack track, Float speed) {
        
        PlaylistDatabase playlistDB = PlaylistDatabase.getInstance();
        playlistDB.addTrackToPlaylist(event.getGuild().getIdLong(), name, track.getInfo().title, track.getInfo().uri);

        this.loadingSongs--;
        if (this.loadingSongs == 0) {
            MessageSender.infoEvent(musicManager.getMessageSender(), "Sons ajouté à " + name, event);
            lock.unlock();
        }
    }
}
