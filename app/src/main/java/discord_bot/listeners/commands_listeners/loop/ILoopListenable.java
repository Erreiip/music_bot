package discord_bot.listeners.commands_listeners.loop;

public interface ILoopListenable extends ILoopListener {
    
    public void addLoopListener(ILoopListener listener);
    
    public void removeLoopListener(ILoopListener listener);
}
