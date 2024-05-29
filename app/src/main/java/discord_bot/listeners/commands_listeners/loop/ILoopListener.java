package discord_bot.listeners.commands_listeners.loop;

import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public interface ILoopListener {
    
    public void onLoop(IDeferrableCallback event);
}
