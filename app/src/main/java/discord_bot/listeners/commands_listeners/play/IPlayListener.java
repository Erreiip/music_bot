package discord_bot.listeners.commands_listeners.play;

import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public interface IPlayListener {
    
    public void onPlay(IDeferrableCallback event);

    public void onPlayQueue(IDeferrableCallback event);
}
