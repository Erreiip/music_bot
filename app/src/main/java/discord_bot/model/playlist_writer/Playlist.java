package discord_bot.model.playlist_writer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import discord_bot.jda.Kawaine;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.utils.Couple;
import discord_bot.utils.IProcessAudio;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import okhttp3.internal.ws.RealWebSocket.Message;

public class Playlist {

    private static final String PLAYLISTS_PATH = "playlists/";

    private final String name;
    private List<AudioTrackInfo> tracks;

    public Playlist(String name) {

        this.name = name;
        this.tracks = new ArrayList<>();
    }

    public void addTrack(AudioTrackInfo track) {

        for (AudioTrackInfo t : this.tracks) {

            if (t.title.equals(track.title) && t.author.equals(track.author)) return;
        }
        
        this.tracks.add(track);
    }

    public boolean removeTrack(int index) {

        if ( index >= this.tracks.size() || index < 0 ) return false;

        this.tracks.remove(index);

        return true;
    }

    public boolean removeTrack(String trackName) {

        for (AudioTrackInfo track : this.tracks) {

            if (track.title.equals(trackName)) {

                this.tracks.remove(track);
                return true;
            }
        }

        return false;
    }

    public List<AudioTrackInfo> getTracks() {

        return this.tracks;
    }

    public String getName() {

        return this.name;
    }

    public Couple<String, List<AudioTrackInfo>> toCouple() {

        return new Couple<>(this.name, this.tracks);
    }
    
    public static List<String> getPlaylistsNames() {

        createPlaylistDirectory();

        List<String> playlists = new ArrayList<>();
        File folder = new File(PLAYLISTS_PATH);
        File[] files = folder.listFiles();

        for (File file : files) {

            String name = file.getName().replace(".txt", "");

            playlists.add(name);
        }

        return playlists;
    }

    public static Playlist readPlaylist(String name) throws Exception {
        
        File file = new File(PLAYLISTS_PATH + name + ".txt");
        
        if (!file.exists()) {
            
            throw new Exception("Playlist not found");
        }
        
        Playlist playlist = new Playlist(name);

        BufferedReader buffer = null;
        String line;

        try { buffer = new BufferedReader(new FileReader(file)); }
        catch(FileNotFoundException exc) { exc.printStackTrace(); }
        
        while ((line = buffer.readLine()) != null) {

            String[] parts = line.split("\\|\\|");
            AudioTrackInfo track = new AudioTrackInfo(parts[0], parts[1], Long.parseLong(parts[2]), parts[3], Boolean.parseBoolean(parts[4]), parts[5]);
            playlist.addTrack(track);
        }
        
        buffer.close();

        return playlist;
    }

    public static void writePlaylist(Playlist playlist) throws Exception {

        File file = createPlaylist(playlist.name);

        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (AudioTrackInfo track : playlist.getTracks()) {

            printWriter.println(
                track.title + "||" + track.author + "||" + track.length + "||" + 
                track.identifier + "||" + track.isStream + "||" + track.uri
            );
        }

        printWriter.close();
    }

    public static File createPlaylist(String name) {

        createPlaylistDirectory();

        File file = new File(PLAYLISTS_PATH + name + ".txt");

        try { file.createNewFile(); }
        catch (Exception exc) { exc.printStackTrace(); }

        return file;
    }
    
    private static void createPlaylistDirectory() {
        
        File file = new File(PLAYLISTS_PATH);
        
        if (!file.exists()) {
            
            file.mkdir();
        }
    }

    /*
    @Override
    public void onTrackGet(SlashCommandInteractionEvent event, AudioTrack track, Float speed) {
        
        this.addTrack(track.getInfo());

        try { Playlist.writePlaylist(this); }
        catch (Exception e) { 
            MessageSender.errorEvent(kawaine.getMessageSender(), "An error occurred while saving the playlist.", event);
            return;
        }

        MessageSender.infoEvent(kawaine.getMessageSender(), "Track " + track.getInfo().title + "to " + this.name, event);
    }
    */
}
