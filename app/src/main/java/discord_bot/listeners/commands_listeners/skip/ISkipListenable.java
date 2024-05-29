package discord_bot.listeners.commands_listeners.skip;

public interface ISkipListenable extends ISkipListener {
    
    public void addSkipListener(ISkipListener listener);

    public void removeSkipListener(ISkipListener listener);
}
