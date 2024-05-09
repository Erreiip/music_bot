package discord_bot.commands_listeners.play;

import discord_bot.jda_listener.model.TrackScheduler;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public interface IPlayListener {
    
    public void onPlay(IDeferrableCallback event, TrackScheduler scheduler);

    public void onPlayQueue(IDeferrableCallback event, TrackScheduler scheduler);
}
