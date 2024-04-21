package discord_bot.lava_player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.checkerframework.checker.units.qual.A;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer player;
    private final List<AudioTrack> queue;
    private boolean loop;
    private AudioTrack currentTrack;

    public TrackScheduler(AudioPlayer player) {

        this.player = player;
        this.loop = false;
        this.queue = new ArrayList<>();
    }

    public void queue(AudioTrack track) {

        if (!player.startTrack(track, true)) {
            queue.add(track);
            return;
        }

        this.currentTrack = track.makeClone();

        // if this is the only one track
        if ( this.loop ) this.queue.add(this.currentTrack);
    }

    public void nextTrack() {

        if (queue.isEmpty()) {
            player.stopTrack();
            return;
        }

        this.currentTrack = queue.get(0).makeClone();

        player.startTrack(this.currentTrack, false);

        if (!this.loop) {
            this.queue.remove(0);
        }
    }
    
    public void addLastTrack() {

        this.queue.add(currentTrack);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {

        if (endReason.mayStartNext || endReason == AudioTrackEndReason.FINISHED && this.loop) {
            nextTrack();
            return;
        }
    }

    public boolean switchLoop() {
        return setLoop(!this.loop);
    }

    public boolean setLoop( boolean state) {
        this.loop = state;

        if (this.loop) {
            if (this.currentTrack != null) {
                this.queue.add(0, this.currentTrack);
            }
        } else {
            if (this.currentTrack != null) {
                this.queue.remove(0);
            }
        }

        return this.loop;
    }
    
}
