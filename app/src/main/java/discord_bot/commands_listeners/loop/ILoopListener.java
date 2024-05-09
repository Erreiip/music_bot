package discord_bot.commands_listeners.loop;

import discord_bot.jda_listener.model.TrackScheduler;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public interface ILoopListener {
    
    public void onLoop(IDeferrableCallback event, TrackScheduler scheduler);
}
