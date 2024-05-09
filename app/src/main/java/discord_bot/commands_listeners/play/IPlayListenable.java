package discord_bot.commands_listeners.play;

import discord_bot.jda_listener.model.TrackScheduler;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public interface IPlayListenable {
    
    public void addPlayListener(IPlayListener listener);

    public void removePlayListener(IPlayListener listener);

    public void firePlay(IDeferrableCallback event, TrackScheduler scheduler);

    public void firePlayQueue(IDeferrableCallback event, TrackScheduler scheduler);
}
