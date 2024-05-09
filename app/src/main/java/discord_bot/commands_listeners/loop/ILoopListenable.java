package discord_bot.commands_listeners.loop;

import discord_bot.jda_listener.model.TrackScheduler;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public interface ILoopListenable {
    
    public void addLoopListener(ILoopListener listener);
    
    public void removeLoopListener(ILoopListener listener);
    
    public void fireLoop(IDeferrableCallback event, TrackScheduler scheduler);
}
