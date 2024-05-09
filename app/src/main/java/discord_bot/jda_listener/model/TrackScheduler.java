package discord_bot.jda_listener.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.natanbc.lavadsp.timescale.TimescalePcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import discord_bot.commands_listeners.loop.ILoopListenable;
import discord_bot.commands_listeners.loop.ILoopListener;
import discord_bot.commands_listeners.play.IPlayListenable;
import discord_bot.commands_listeners.play.IPlayListener;
import discord_bot.commands_listeners.skip.ISkipListenable;
import discord_bot.commands_listeners.skip.ISkipListener;
import discord_bot.common.Couple;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class TrackScheduler extends AudioEventAdapter implements ISkipListenable, IPlayListenable, ILoopListenable {

    private final AudioPlayer player;
    private final AudioPlayer secondPlayer;
    private final List<Couple<AudioTrack, Float>> queue;
    private boolean loop;

    private GenericInteractionCreateEvent event;
    private AudioTrack currentTrack;
    private Float currentTrackSpeed;

    public TrackScheduler(AudioPlayer player, AudioPlayer secondPlayer) {

        this.player = player;
        this.secondPlayer = secondPlayer;
        this.loop = false;

        this.event = null;

        this.queue = new ArrayList<>();
    }

    public boolean queueWithoutFire(AudioTrack track, float speed) {

        boolean canStart = player.startTrack(track, true);

        if (!canStart) {
            queue.add(new Couple<AudioTrack, Float>(track, speed));
            return false;
        }

        changePlayerSpeed(speed);

        this.currentTrack = track;
        this.currentTrackSpeed = speed;

        return true;
    }

    public void queueWithoutFire(AudioTrack track, Float speed, GenericInteractionCreateEvent event) {
            
        this.event = event;

        this.queueWithoutFire(track, speed);
    }

    public void queue(AudioTrack track, Float speed) {

        if ( ! this.queueWithoutFire(track, speed) ) {
            this.firePlayQueue((IDeferrableCallback)event, this);
            return;
        }
    
        this.firePlay((IDeferrableCallback)event, this);
    }

    public void queue(AudioTrack track, Float speed, GenericInteractionCreateEvent event) {

        this.event = event;

        this.queue(track, speed);
    }

    public void nextTrack() {

        if (queue.isEmpty() && ! this.loop) {
            this.currentTrack = null;
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

        this.fireSkip((IDeferrableCallback) event, this);
    }

    public void nextTrack(GenericInteractionCreateEvent event) {

        this.event = event;

        this.nextTrack();
    }
    
    public void addLastTrack() {

        this.queue(currentTrack, currentTrackSpeed);
    }

    public AudioTrack getCurrentTrack() {
        return this.currentTrack;
    }

    public List<AudioTrack> getQueue() {

        List<AudioTrack> tracks = new ArrayList<>();

        if (this.currentTrack != null) {
            tracks.add(this.currentTrack);
        }

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
        
        if ( loop == state ) return;

        this.loop = state;
        this.fireLoop((IDeferrableCallback) event, this);
    }


    /*
     * ISkipListenable
     */
    private List<ISkipListener> listenersSkip = new ArrayList<>();

    @Override
    public void addSkipListener(ISkipListener listener) {
        listenersSkip.add(listener);
    }

    @Override
    public void removeSkipListener(ISkipListener listener) {
        listenersSkip.remove(listener);
    }

    @Override
    public void fireSkip(IDeferrableCallback event, TrackScheduler scheduler) {

        for (ISkipListener listener : listenersSkip) {
            listener.onSkip(event, scheduler);
        }
    }


    /*
     * IPlayListenable
     */
    List<IPlayListener> listenersPlay = new ArrayList<>();
     
    @Override
    public void addPlayListener(IPlayListener listener) {
        
        listenersPlay.add(listener);
    }

    @Override
    public void removePlayListener(IPlayListener listener) {
       
        listenersPlay.remove(listener);
    }

    @Override
    public void firePlay(IDeferrableCallback event, TrackScheduler scheduler) {
        
        for (IPlayListener listener : listenersPlay) {
            listener.onPlay(event, scheduler);
        }
    }

    @Override
    public void firePlayQueue(IDeferrableCallback event, TrackScheduler scheduler) {
        
        for (IPlayListener listener : listenersPlay) {
            listener.onPlayQueue(event, scheduler);
        }
    }

    /*
     * ILoopListenable
     */

    List<ILoopListener> listenersLoop = new ArrayList<>();
    
    @Override
    public void addLoopListener(ILoopListener listener) {
        listenersLoop.add(listener);
    }

    @Override
    public void removeLoopListener(ILoopListener listener) {
        listenersLoop.remove(listener);
    }

    @Override
    public void fireLoop(IDeferrableCallback event, TrackScheduler scheduler) {
        
        for (ILoopListener listener : listenersLoop) {
            listener.onLoop(event, scheduler);
        }
    }

}
