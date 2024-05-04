package discord_bot.commands_listeners.skip;

import discord_bot.TrackScheduler;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public interface ISkipListener {
    
    public void onSkip(IDeferrableCallback event, TrackScheduler scheduler);
}
