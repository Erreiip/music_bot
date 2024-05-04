package discord_bot.commands_listeners.skip;

import discord_bot.TrackScheduler;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public interface ISkipListenable {
    
    public void addSkipListener(ISkipListener listener);

    public void removeSkipListener(ISkipListener listener);

    public void fireSkip(IDeferrableCallback event, TrackScheduler scheduler);
}
