package discord_bot.lava_player;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.Kawaine;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AudioLoadResultHandlerImpl implements AudioLoadResultHandler {

    private final GuildMusicManager musicManager;
    private final String songIdentifier;
    private final Kawaine kawai;
    private final SlashCommandInteractionEvent event;

    public AudioLoadResultHandlerImpl(GuildMusicManager musicManager, String songIdentifier, Kawaine kawai, SlashCommandInteractionEvent event) {

        this.musicManager = musicManager;
        this.songIdentifier = songIdentifier;
        this.kawai = kawai;
        this.event = event;
    }

    @Override
    public void trackLoaded(AudioTrack track) {

        event.reply("Adding to queue " + track.getInfo().title).queue();

        kawai.play(event, musicManager, track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {

        AudioTrack firstTrack = playlist.getSelectedTrack();

        if (firstTrack == null) {
            firstTrack = playlist.getTracks().get(0);
        }

        event.reply("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

        kawai.play(event, musicManager, firstTrack);
    }

    @Override
    public void noMatches() {

        event.reply("Nothing found by " + songIdentifier).queue();
    }

    @Override
    public void loadFailed(FriendlyException exception) {

        event.reply("Could not play: " + exception.getMessage()).queue();
        exception.printStackTrace();
    }
    
}
