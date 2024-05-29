package discord_bot.listeners.commands_listeners.skip;

import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public interface ISkipListener {
    
    public void onSkip(IDeferrableCallback event);
}
