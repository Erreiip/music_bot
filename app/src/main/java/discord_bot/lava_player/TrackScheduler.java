package discord_bot.lava_player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.natanbc.lavadsp.timescale.TimescalePcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import discord_bot.common.Couple;

public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer player;
    private final AudioPlayer secondPlayer;
    private final List<Couple<AudioTrack, Float>> queue;
    private boolean loop;

    private AudioTrack currentTrack;
    private Float currentTrackSpeed;

    public TrackScheduler(AudioPlayer player, AudioPlayer secondPlayer) {

        this.player = player;
        this.secondPlayer = secondPlayer;
        this.loop = false;

        this.queue = new ArrayList<>();
    }

    public void queue(AudioTrack track, Float speed) {

        if (!player.startTrack(track, true)) {
            queue.add(new Couple<AudioTrack, Float>(track, speed));
            return;
        }

        changePlayerSpeed(speed);

        this.currentTrack = track.makeClone();
        this.currentTrackSpeed = speed;
    }

    public void nextTrack() {

        if (queue.isEmpty() && ! this.loop) {
            this.player.stopTrack();
            return;
        }

        if (this.loop) {

            this.currentTrack = this.currentTrack.makeClone();
        } else {

            Couple<AudioTrack, Float> queue = this.queue.remove(0);

            this.currentTrack = queue.first.makeClone();
            this.currentTrackSpeed = queue.second;
        }
        
        changePlayerSpeed(currentTrackSpeed);
        player.startTrack(this.currentTrack, false);
    }
    
    public void addLastTrack() {

        this.queue(currentTrack, currentTrackSpeed);
    }

    public AudioTrack getCurrentTrack() {
        return this.currentTrack;
    }

    public List<AudioTrack> getQueue() {

        List<AudioTrack> tracks = new ArrayList<>();

        for (Couple<AudioTrack, Float> couple : this.queue) {
            tracks.add(couple.first);
        }

        return tracks;
    }
    
    private void changePlayerSpeed(Float speed) {

        player.setFilterFactory((trackPlayed, format, output) -> {

            TimescalePcmAudioFilter audioFilter = new TimescalePcmAudioFilter(output, format.channelCount, format.sampleRate);
            audioFilter.setSpeed(speed);
            return Collections.singletonList(audioFilter);
        });
    }
    
    public void clearQueue() {
     
        this.setLoop(false);
        this.queue.clear();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {

        if (endReason.mayStartNext || endReason == AudioTrackEndReason.FINISHED && this.loop) {
            nextTrack();
            return;
        }
    }

    public boolean isLooped() {
        return this.loop;
    }

    public void switchLoop() {
        setLoop(!this.loop);
    }

    public void setLoop(boolean state) {
        this.loop = state;
    }
}
